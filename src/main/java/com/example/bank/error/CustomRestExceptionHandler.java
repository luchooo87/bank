package com.example.bank.error;

import com.example.bank.response.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

@Slf4j
@ControllerAdvice
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {

  @Override
  protected ResponseEntity<Object> handleMissingServletRequestParameter(
    MissingServletRequestParameterException exception, HttpHeaders headers, HttpStatus status, WebRequest request) {

    ServletWebRequest servlet = ((ServletWebRequest) request);
    String message = exception.getParameterName() + " parameter is missing";
    String path = servlet.getRequest().getRequestURI();
    String method = servlet.getHttpMethod().toString();
    log.error("MissingServletRequestParameterException: {}", exception);
    return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, message, path, method));
  }

  @Override
  protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException exception,
                                                                   HttpHeaders headers, HttpStatus status, WebRequest request) {

    ServletWebRequest servlet = ((ServletWebRequest) request);
    StringBuilder builder = new StringBuilder();
    builder.append(exception);
    builder.append(" media type is not supported. Supported media types are ");
    exception.getSupportedMediaTypes().forEach(t -> builder.append(t).append(", "));
    String message = builder.substring(0, builder.length() - 2);
    String path = servlet.getRequest().getRequestURI();
    String method = servlet.getHttpMethod().toString();
    log.error("HttpMediaTypeNotSupportedException: {}", exception);
    return buildResponseEntity(new ApiError(HttpStatus.UNSUPPORTED_MEDIA_TYPE, message, path, method));
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
                                                                HttpHeaders headers, HttpStatus status, WebRequest request) {

    ServletWebRequest servlet = ((ServletWebRequest) request);
    String message = "Validation error";
    String path = servlet.getRequest().getRequestURI();
    String method = servlet.getHttpMethod().toString();
    ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, message, path, method);
    apiError.addValidationErrors(exception.getBindingResult().getFieldErrors());
    apiError.addValidationError(exception.getBindingResult().getGlobalErrors());
    log.error("MethodArgumentNotValidException: {}", exception);
    return buildResponseEntity(apiError);
  }

  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException exception,
                                                                HttpHeaders headers, HttpStatus status, WebRequest request) {

    ServletWebRequest servlet = ((ServletWebRequest) request);
    String message = "Malformed JSON request";
    String path = servlet.getRequest().getRequestURI();
    String method = servlet.getHttpMethod().toString();
    log.error("HttpMessageNotReadableException: {}", exception);
    return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, message, path, method));
  }

  @Override
  protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException exception,
                                                                HttpHeaders headers, HttpStatus status, WebRequest request) {

    ServletWebRequest servlet = ((ServletWebRequest) request);
    String message = "Error writing JSON output";
    String path = servlet.getRequest().getRequestURI();
    String method = servlet.getHttpMethod().toString();
    log.error("HttpMessageNotWritableException: {}", exception);
    return buildResponseEntity(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, message, path, method));
  }

  @Override
  protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException exception, HttpHeaders headers,
                                                                 HttpStatus status, WebRequest request) {

    ServletWebRequest servlet = ((ServletWebRequest) request);
    String message = String.format("Could not find the %s method for URL %s", exception.getHttpMethod(),
      exception.getRequestURL());
    String path = servlet.getRequest().getRequestURI();
    String method = servlet.getHttpMethod().toString();
    log.error("NoHandlerFoundException {}", exception);
    return buildResponseEntity(new ApiError(HttpStatus.NOT_FOUND, message, path, method));

  }

  @ExceptionHandler(javax.validation.ConstraintViolationException.class)
  protected ResponseEntity<Object> handleConstraintViolation(javax.validation.ConstraintViolationException exception,
                                                             HttpServletRequest request) {

    String message = "Validation error";
    String path = request.getServletPath();
    String method = request.getMethod();
    ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, message, path, method);
    apiError.addValidationErrors(exception.getConstraintViolations());
    log.error("javax.validation.ConstraintViolationException: {}", exception);
    return buildResponseEntity(apiError);
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  protected ResponseEntity<Object> handleDataIntegrityViolation(DataIntegrityViolationException exception,
                                                                HttpServletRequest request) {

    HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
    String message = "Could not process transaction";
    String path = request.getServletPath();
    String method = request.getMethod();
    log.error("DataIntegrityViolationException: {}", exception);
    return buildResponseEntity(new ApiError(status, message, path, method));
  }

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException exception,
                                                                    HttpServletRequest request) {

    String message = String.format("The parameter '%s' of value '%s' could not be converted to type '%s'",
      exception.getName(), exception.getValue(), exception.getRequiredType().getSimpleName());
    String path = request.getServletPath();
    String method = request.getMethod();
    log.error("MethodArgumentTypeMismatchException: {}", exception);
    return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, message, path, method));
  }

  @ExceptionHandler(TransactionSystemException.class)
  public ResponseEntity<Object> handleTransactionSystemException(TransactionSystemException exception,
                                                                 HttpServletRequest request) {

    String message = "Could not process transaction";
    String path = request.getServletPath();
    String method = request.getMethod();
    log.error("TransactionSystemException: {}", exception);
    return buildResponseEntity(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, message, path, method));
  }

  @ExceptionHandler(SQLException.class)
  public ResponseEntity<Object> handleSQLException(SQLException exception, HttpServletRequest request) {
    String message = "Database error";
    String path = request.getServletPath();
    String method = request.getMethod();
    log.error("SQLException: {}", exception);
    return buildResponseEntity(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, message, path, method));
  }

  @ExceptionHandler(DataAccessException.class)
  public ResponseEntity<Object> handleDataAccessException(DataAccessException exception, HttpServletRequest request) {
    String message = "Database Error";
    String path = request.getServletPath();
    String method = request.getMethod();
    log.error("DataAccessException: {}", exception);
    return buildResponseEntity(new ApiError(HttpStatus.CONFLICT, message, path, method));
  }

  @ExceptionHandler(InternalServerException.class)
  public final ResponseEntity<Object> handleInternalServerException(InternalServerException exception,
                                                                    HttpServletRequest request) {

    String message = "Could not process transaction";
    String path = request.getServletPath();
    String method = request.getMethod();
    log.error("InternalServerException: {}", exception);
    return buildResponseEntity(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, message, path, method));
  }

  @ExceptionHandler(NotFoundException.class)
  public final ResponseEntity<Object> handleNotFoundException(NotFoundException exception, HttpServletRequest request) {
    String message = exception.getMessage();
    String path = request.getServletPath();
    String method = request.getMethod();
    log.error("NotFoundException: {}", exception);
    return buildResponseEntity(new ApiError(HttpStatus.NOT_FOUND, message, path, method));
  }

  @ExceptionHandler(UnautorizedException.class)
  public final ResponseEntity<Object> handleUnautorizedException(UnautorizedException exception,
                                                                 HttpServletRequest request) {

    String message = "Unautorized resource";
    String path = request.getServletPath();
    String method = request.getMethod();
    log.error("UnautorizedException: {}", exception);
    return buildResponseEntity(new ApiError(HttpStatus.UNAUTHORIZED, message, path, method));
  }

  @ExceptionHandler(BadRequestException.class)
  public final ResponseEntity<Object> handleBadRequestException(BadRequestException exception,
                                                                HttpServletRequest request) {

    String message = exception.getMessage();
    String path = request.getServletPath();
    String method = request.getMethod();
    log.error("BadRequestException: {}", exception);
    return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, message, path, method));
  }

  @ExceptionHandler(ConflictRequestException.class)
  public final ResponseEntity<Object> handleBadRequestException(ConflictRequestException exception,
                                                                HttpServletRequest request) {

    String message = exception.getMessage();
    String path = request.getServletPath();
    String method = request.getMethod();
    log.error("ConflictRequestException: {}", exception);
    return buildResponseEntity(new ApiError(HttpStatus.CONFLICT, message, path, method));
  }

  @ExceptionHandler({ NumberFormatException.class })
  public ResponseEntity<Object> handleNumberFormatException(NumberFormatException exception,
                                                            HttpServletRequest request) {

    String message = "Can't cast value to number";
    String path = request.getServletPath();
    String method = request.getMethod();
    log.error("NumberFormatException: {}", exception);
    return buildResponseEntity(new ApiError(HttpStatus.CONFLICT, message, path, method));
  }

  private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
    return new ResponseEntity<Object>(apiError, apiError.getStatus());
  }

}
