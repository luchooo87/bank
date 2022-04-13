package com.example.bank.services;

import com.example.bank.dto.read.MovementRDto;
import com.example.bank.model.Account;
import com.example.bank.model.AccountMovement;
import com.example.bank.model.Client;
import com.example.bank.repository.IAccountMovementsRepository;
import com.example.bank.services.impl.MovementRService;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;

@ExtendWith(SpringExtension.class)
public class MovementRServiceTest {

    @Mock
    private IAccountMovementsRepository _accountMovementsRepository;

    @InjectMocks
    private MovementRService _movementRService;

    AccountMovement movement1 = new AccountMovement();
    AccountMovement movement2 = new AccountMovement();
    AccountMovement movement3 = new AccountMovement();
    List<AccountMovement> movements = new ArrayList<>();

    Account account1 = new Account();

    Client client1 = new Client();

    @BeforeEach
    void initUseCase() {
        this.client1.setId(1);
        this.client1.setStatus(true);
        this.client1.setAddress("Otavalo sn y principal");
        this.client1.setPhone("098254785");
        this.client1.setPassword("1234");
        this.client1.setFullName("Jose Lema");
        this.client1.setAge(21);
        this.client1.setIdentification("123456789");

        this.account1.setId(1);
        this.account1.setAccountType("Ahorro");
        this.account1.setNumber("478758");
        this.account1.setInitialBalance(new BigDecimal(2000));
        this.account1.setStatus(true);
        this.account1.setClient(this.client1);

        this.movement1.setId(1);
        this.movement1.setMovementValue(new BigDecimal(2000));
        this.movement1.setMovementType("INICIAL");
        this.movement1.setBalance(new BigDecimal(2000));
        this.movement1.setMovementDate(Timestamp.valueOf("2022-04-07 09:24:23"));

        this.movement2.setId(2);
        this.movement2.setMovementValue(new BigDecimal(50));
        this.movement2.setMovementType("Deposito");
        this.movement2.setBalance(new BigDecimal(2050));
        this.movement2.setMovementDate(Timestamp.valueOf("2022-04-09 14:10:48"));

        this.movement3.setId(3);
        this.movement3.setMovementValue(new BigDecimal(-100));
        this.movement3.setMovementType("Retiro");
        this.movement3.setBalance(new BigDecimal(1950));
        this.movement3.setMovementDate(Timestamp.valueOf("2022-04-12 11:04:12"));

        this.movements = Arrays.asList(this.movement1, this.movement2, this.movement3);
    }

    @Test
    public void getByIdTest() {
        Mockito.when(this._accountMovementsRepository.findById(2)).thenReturn(Optional.of(this.movement2));
        AccountMovement movement = this._movementRService.getById(2);
        Mockito.verify(this._accountMovementsRepository, Mockito.times(1)).findById(2);

        MatcherAssert.assertThat(movement, CoreMatchers.is(CoreMatchers.notNullValue()));
        MatcherAssert.assertThat(movement.getId(), Matchers.equalTo(2));
        MatcherAssert.assertThat(movement.getMovementType(), Matchers.equalTo("Deposito"));
        MatcherAssert.assertThat(movement.getMovementValue(), Matchers.equalTo(new BigDecimal(50)));
        MatcherAssert.assertThat(movement.getBalance(), Matchers.equalTo(new BigDecimal(2050)));
        MatcherAssert.assertThat(movement.getMovementDate(), Matchers.equalTo(Timestamp.valueOf("2022-04-09 14:10:48")));

    }

    @Test
    public void getByAccountNumberTest() {
        Mockito.when(this._accountMovementsRepository.findByAccount_Number("478758")).thenReturn(this.movements);
        List<AccountMovement> result = this._movementRService.getByAccountNumber("478758");
        Mockito.verify(this._accountMovementsRepository, Mockito.times(1))
                .findByAccount_Number("478758");

        MatcherAssert.assertThat(result, Matchers.hasSize(3));
        MatcherAssert.assertThat(result.get(0).getId(), Matchers.equalTo(1));
        MatcherAssert.assertThat(result.get(0).getMovementType(), Matchers.equalTo("INICIAL"));
        MatcherAssert.assertThat(result.get(0).getBalance(), Matchers.equalTo(new BigDecimal(2000)));
        MatcherAssert.assertThat(result.get(0).getMovementDate(), Matchers.equalTo(Timestamp.valueOf("2022-04-07 09:24:23")));

        MatcherAssert.assertThat(result.get(1).getId(), Matchers.equalTo(2));
        MatcherAssert.assertThat(result.get(1).getMovementType(), Matchers.equalTo("Deposito"));
        MatcherAssert.assertThat(result.get(1).getBalance(), Matchers.equalTo(new BigDecimal(2050)));
        MatcherAssert.assertThat(result.get(1).getMovementDate(), Matchers.equalTo(Timestamp.valueOf("2022-04-09 14:10:48")));

        MatcherAssert.assertThat(result.get(2).getId(), Matchers.equalTo(3));
        MatcherAssert.assertThat(result.get(2).getMovementType(), Matchers.equalTo("Retiro"));
        MatcherAssert.assertThat(result.get(2).getBalance(), Matchers.equalTo(new BigDecimal(1950)));
        MatcherAssert.assertThat(result.get(2).getMovementDate(), Matchers.equalTo(Timestamp.valueOf("2022-04-12 11:04:12")));

    }

    @Test
    public void getByRangeDateTest() throws ParseException {
        Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse("2022-04-01");
        Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse("2022-04-12");

        Mockito.when(this._accountMovementsRepository.findByMovementDateBetweenAndAccount_Number
                        (this.getStartDate(startDate), this.getEndDate(endDate), "478758"))
                .thenReturn(this.movements);

        List<AccountMovement> result = this._movementRService.getByRangeDate(startDate, endDate, "478758");
        Mockito.verify(this._accountMovementsRepository, Mockito.times(1))
                .findByMovementDateBetweenAndAccount_Number(
                        this.getStartDate(startDate), this.getEndDate(endDate), "478758");

        MatcherAssert.assertThat(result, Matchers.hasSize(3));
        MatcherAssert.assertThat(result.get(0).getId(), Matchers.equalTo(1));
        MatcherAssert.assertThat(result.get(0).getMovementType(), Matchers.equalTo("INICIAL"));
        MatcherAssert.assertThat(result.get(0).getBalance(), Matchers.equalTo(new BigDecimal(2000)));
        MatcherAssert.assertThat(result.get(0).getMovementDate(), Matchers.equalTo(Timestamp.valueOf("2022-04-07 09:24:23")));

        MatcherAssert.assertThat(result.get(1).getId(), Matchers.equalTo(2));
        MatcherAssert.assertThat(result.get(1).getMovementType(), Matchers.equalTo("Deposito"));
        MatcherAssert.assertThat(result.get(1).getBalance(), Matchers.equalTo(new BigDecimal(2050)));
        MatcherAssert.assertThat(result.get(1).getMovementDate(), Matchers.equalTo(Timestamp.valueOf("2022-04-09 14:10:48")));

        MatcherAssert.assertThat(result.get(2).getId(), Matchers.equalTo(3));
        MatcherAssert.assertThat(result.get(2).getMovementType(), Matchers.equalTo("Retiro"));
        MatcherAssert.assertThat(result.get(2).getBalance(), Matchers.equalTo(new BigDecimal(1950)));
        MatcherAssert.assertThat(result.get(2).getMovementDate(), Matchers.equalTo(Timestamp.valueOf("2022-04-12 11:04:12")));

    }

    @Test
    public void getReportTest() throws ParseException {
        MovementRDto movementRDto1 = new MovementRDto(Timestamp.valueOf("2022-04-01 12:34:56")
                , "Jose Lema", "478758", "DEPOSITO", new BigDecimal(2000), true,
                new BigDecimal(575), new BigDecimal(2575));

        MovementRDto movementRDto2 = new MovementRDto(Timestamp.valueOf("2022-04-07 09:21:04"),
                "Jose Lema", "478758", "RETIRO", new BigDecimal(2000), true, new BigDecimal(-100),
                new BigDecimal(2475));

        List<MovementRDto> report = Arrays.asList(movementRDto1, movementRDto2);

        Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse("2022-04-01");
        Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse("2022-04-12");

        Mockito.when(this._accountMovementsRepository.generateReport
                        (this.getStartDate(startDate), this.getEndDate(endDate), "478758"))
                .thenReturn(report);

        List<MovementRDto> result = this._movementRService.getReport(startDate, endDate, "478758");
        Mockito.verify(this._accountMovementsRepository, Mockito.times(1))
                .generateReport(this.getStartDate(startDate), this.getEndDate(endDate), "478758");

        MatcherAssert.assertThat(result, Matchers.hasSize(2));
        MatcherAssert.assertThat(result.get(0).getSaldoDisponible(), Matchers.equalTo(new BigDecimal(2575)));
        MatcherAssert.assertThat(result.get(0).getTipo(), Matchers.equalTo("DEPOSITO"));
        MatcherAssert.assertThat(result.get(0).getClient(), Matchers.equalTo("Jose Lema"));
        MatcherAssert.assertThat(result.get(0).getFecha(), Matchers.equalTo(Timestamp.valueOf("2022-04-01 12:34:56")));

        MatcherAssert.assertThat(result.get(1).getSaldoDisponible(), Matchers.equalTo(new BigDecimal(2475)));
        MatcherAssert.assertThat(result.get(1).getTipo(), Matchers.equalTo("RETIRO"));
        MatcherAssert.assertThat(result.get(1).getMovimiento(), Matchers.equalTo(new BigDecimal(-100)));
        MatcherAssert.assertThat(result.get(1).getFecha(), Matchers.equalTo(Timestamp.valueOf("2022-04-07 09:21:04")));
    }

    private Date getStartDate(Date date) {
        LocalDate localEndDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDateTime startOfDay = localEndDate.atTime(LocalTime.MIN);
        return Date.from(startOfDay.atZone(ZoneId.systemDefault()).toInstant());
    }

    private Date getEndDate(Date date) {
        LocalDate localEndDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDateTime endOfDay = localEndDate.atTime(LocalTime.MAX);
        return Date.from(endOfDay.atZone(ZoneId.systemDefault()).toInstant());
    }
}
