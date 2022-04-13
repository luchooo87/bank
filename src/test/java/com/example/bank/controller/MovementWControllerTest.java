package com.example.bank.controller;

import com.example.bank.controller.impl.MovementWController;
import com.example.bank.dto.write.MovementWDto;
import com.example.bank.model.Account;
import com.example.bank.model.AccountMovement;
import com.example.bank.model.Client;
import com.example.bank.services.impl.MovementWService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.sql.Timestamp;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
public class MovementWControllerTest {

    @Mock
    private MovementWService _movementWService;

    @InjectMocks
    private MovementWController _movementWController;

    private MockMvc mockMvc;

    AccountMovement movement1 = new AccountMovement();
    Account account1 = new Account();
    Client client1 = new Client();

    @BeforeEach
    void initUseCase() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(this._movementWController).build();
        ReflectionTestUtils.setField(this._movementWService, "withdrawalLimit", "1000");
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

        this.movement1.setMovementType("DEPOSITO");
        this.movement1.setMovementValue(new BigDecimal(100));
        this.movement1.setBalance(new BigDecimal(100));
        this.movement1.setMovementDate(Timestamp.valueOf("2022-04-07 09:21:04"));
        this.movement1.setAccount(this.account1);
        this.movement1.setStatus(true);
        this.movement1.setId(1);
    }

    @Test
    public void createFunctionTest() {
        MovementWDto movementWDto = new MovementWDto();
        movementWDto.setMovementValue(new BigDecimal(100));
        movementWDto.setTypeMovement("DEPOSITO");
        movementWDto.setNumberAccount("478758");

        Mockito.when(this._movementWService.create(movementWDto)).thenReturn(this.movement1);
        ResponseEntity<?> responseEntity = this._movementWController.create(movementWDto);
        Mockito.verify(this._movementWService, Mockito.times(1)).create(movementWDto);
        MatcherAssert.assertThat(responseEntity.getStatusCodeValue(), Matchers.equalTo(201));
    }

    @Test
    public void createEndpointTest() throws Exception {
        MovementWDto movementWDto = new MovementWDto();
        movementWDto.setMovementValue(new BigDecimal(100));
        movementWDto.setTypeMovement("DEPOSITO");
        movementWDto.setNumberAccount("478758");

        Mockito.when(this._movementWService.create(movementWDto)).thenReturn(this.movement1);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(movementWDto);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/movimientos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))

                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print());

        Mockito.verify(this._movementWService, Mockito.times(1)).create(movementWDto);
    }

    @Test
    public void deleteFunctionTest() {
        Mockito.when(this._movementWService.delete(1)).thenReturn(this.movement1);
        ResponseEntity<?> responseEntity = this._movementWController.delete(1);
        Mockito.verify(this._movementWService, Mockito.times(1)).delete(1);
        MatcherAssert.assertThat(responseEntity.getStatusCodeValue(), Matchers.equalTo(200));
    }

    @Test
    public void deleteEndpointTest() throws Exception {
        Mockito.when(this._movementWService.delete(1)).thenReturn(this.movement1);
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/api/movimientos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
        Mockito.verify(this._movementWService, Mockito.times(1)).delete(1);
    }

}

