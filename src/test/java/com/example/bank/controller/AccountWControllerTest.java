package com.example.bank.controller;

import com.example.bank.controller.impl.AccountWController;
import com.example.bank.dto.write.AccountWDto;
import com.example.bank.model.Account;
import com.example.bank.model.Client;
import com.example.bank.services.impl.AccountWService;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
public class AccountWControllerTest {

    @Mock
    private AccountWService _accountWService;

    @InjectMocks
    private AccountWController _accountWController;

    private MockMvc mockMvc;

    Account account1 = new Account();
    Client client1 = new Client();

    @BeforeEach
    void initUseCase() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(this._accountWController).build();
        this.client1.setId(1);
        this.client1.setStatus(true);
        this.client1.setAddress("Otavalo sn y principal");
        this.client1.setPhone("098254785");
        this.client1.setPassword("1234");
        this.client1.setFullName("Jose Lema");
        this.client1.setAge(21);
        this.client1.setIdentification("123456789");

        this.account1.setId(1);
        this.account1.setAccountType("Ahorros");
        this.account1.setNumber("478758");
        this.account1.setInitialBalance(new BigDecimal(2000));
        this.account1.setStatus(true);
        this.account1.setClient(this.client1);
    }

    @Test
    public void createFunctionTest() {
        AccountWDto accountWDto = new AccountWDto();
        accountWDto.setAccountType("Ahorros");
        accountWDto.setFullName("Jose Lema");
        accountWDto.setNumber("478758");
        accountWDto.setInitialBalance(new BigDecimal(2000));

        Mockito.when(this._accountWService.create(accountWDto)).thenReturn(this.account1);
        ResponseEntity<?> responseEntity = this._accountWController.create(accountWDto);
        Mockito.verify(this._accountWService, Mockito.times(1)).create(accountWDto);
        MatcherAssert.assertThat(responseEntity.getStatusCodeValue(), Matchers.equalTo(201));
    }

    @Test
    public void createEndpointTest() throws Exception {
        AccountWDto accountWDto = new AccountWDto();
        accountWDto.setAccountType("Ahorros");
        accountWDto.setFullName("Jose Lema");
        accountWDto.setNumber("478758");
        accountWDto.setInitialBalance(new BigDecimal(2000));

        Mockito.when(this._accountWService.create(accountWDto)).thenReturn(this.account1);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(accountWDto);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/cuentas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))

                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print());

        Mockito.verify(this._accountWService, Mockito.times(1)).create(accountWDto);
    }

    @Test
    public void updateFunctionTest() {
        AccountWDto accountWDto = new AccountWDto();
        accountWDto.setAccountType("Corriente");
        accountWDto.setFullName("Jose Lema");
        accountWDto.setNumber("478758");
        accountWDto.setInitialBalance(new BigDecimal(50));
        accountWDto.setId(1);

        Account accountUpdated = new Account();
        accountUpdated.setAccountType("Corriente");
        accountUpdated.setInitialBalance(new BigDecimal(50));

        Mockito.when(this._accountWService.update(accountWDto)).thenReturn(accountUpdated);
        ResponseEntity<?> responseEntity = this._accountWController.update(accountWDto);
        Mockito.verify(this._accountWService, Mockito.times(1)).update(accountWDto);
        MatcherAssert.assertThat(responseEntity.getStatusCodeValue(), Matchers.equalTo(200));
    }

    @Test
    public void updateEndpointTest() throws Exception {
        AccountWDto accountWDto = new AccountWDto();
        accountWDto.setAccountType("Corriente");
        accountWDto.setFullName("Jose Lema");
        accountWDto.setNumber("478758");
        accountWDto.setInitialBalance(new BigDecimal(50));
        accountWDto.setId(1);

        Account accountUpdated = new Account();
        accountUpdated.setAccountType("Corriente");
        accountUpdated.setInitialBalance(new BigDecimal(50));

        Mockito.when(this._accountWService.update(accountWDto)).thenReturn(accountUpdated);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(accountWDto);
        this.mockMvc.perform(MockMvcRequestBuilders.put("/api/cuentas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

        Mockito.verify(this._accountWService, Mockito.times(1)).update(accountWDto);
    }

    @Test
    public void deleteFunctionTest() {
        Mockito.when(this._accountWService.delete(1)).thenReturn(this.account1);
        ResponseEntity<?> responseEntity = this._accountWController.delete(1);
        Mockito.verify(this._accountWService, Mockito.times(1)).delete(1);
        MatcherAssert.assertThat(responseEntity.getStatusCodeValue(), Matchers.equalTo(200));
    }

    @Test
    public void deleteEndpointTest() throws Exception {
        Mockito.when(this._accountWService.delete(1)).thenReturn(this.account1);
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/api/cuentas/1")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
        Mockito.verify(this._accountWService, Mockito.times(1)).delete(1);
    }

}

