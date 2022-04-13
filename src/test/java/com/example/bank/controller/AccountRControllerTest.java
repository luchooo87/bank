package com.example.bank.controller;

import com.example.bank.controller.impl.AccountRController;
import com.example.bank.model.Account;
import com.example.bank.model.Client;
import com.example.bank.services.impl.AccountRService;
import org.hamcrest.CoreMatchers;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
public class AccountRControllerTest {

    @Mock
    private AccountRService _accountRService;

    @InjectMocks
    private AccountRController _accountRController;

    private MockMvc mockMvc;

    Account account1 = new Account();
    Account account2 = new Account();
    Account account3 = new Account();
    Account account4 = new Account();
    Account account5 = new Account();
    List<Account> accounts = new ArrayList<>();

    Client client1 = new Client();
    Client client2 = new Client();
    Client client3 = new Client();

    @BeforeEach
    void initUseCase() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(this._accountRController).build();
        this.client1.setId(1);
        this.client1.setStatus(true);
        this.client1.setAddress("Otavalo sn y principal");
        this.client1.setPhone("098254785");
        this.client1.setPassword("1234");
        this.client1.setFullName("Jose Lema");
        this.client1.setAge(21);
        this.client1.setIdentification("123456789");

        this.client2.setId(2);
        this.client2.setStatus(true);
        this.client2.setAddress("Amazonas y NNUU");
        this.client2.setPhone("097548965");
        this.client2.setPassword("5678");
        this.client2.setFullName("Marianela Montalvo");
        this.client2.setAge(24);
        this.client2.setIdentification("123456789");

        this.client3.setId(3);
        this.client3.setStatus(false);
        this.client3.setAddress("13 junio y Equinoccial");
        this.client3.setPhone("098874587");
        this.client3.setPassword("1245");
        this.client3.setFullName("Juan Osorio");
        this.client3.setAge(30);
        this.client3.setIdentification("10036787");

        this.account1.setId(1);
        this.account1.setAccountType("Ahorro");
        this.account1.setNumber("478758");
        this.account1.setInitialBalance(new BigDecimal(2000));
        this.account1.setStatus(true);
        this.account1.setClient(this.client1);

        this.account2.setId(2);
        this.account2.setAccountType("Corriente");
        this.account2.setNumber("225487");
        this.account2.setInitialBalance(new BigDecimal(100));
        this.account2.setStatus(true);
        this.account2.setClient(this.client2);

        this.account3.setId(3);
        this.account3.setAccountType("Ahorros");
        this.account3.setNumber("495878");
        this.account3.setInitialBalance(BigDecimal.ZERO);
        this.account3.setStatus(true);
        this.account3.setClient(this.client3);

        this.account4.setId(4);
        this.account4.setAccountType("Ahorros");
        this.account4.setNumber("496825");
        this.account4.setInitialBalance(new BigDecimal(540));
        this.account4.setStatus(true);
        this.account4.setClient(this.client2);

        this.account5.setId(5);
        this.account5.setAccountType("Ahorros");
        this.account5.setNumber("4968254");
        this.account5.setInitialBalance(new BigDecimal(540));
        this.account5.setStatus(false);
        this.account5.setClient(this.client3);

        this.accounts = Arrays.asList(
                this.account1, this.account2, this.account3, this.account4
        );
    }

    @Test
    public void getAllFunctionTest() {
        Mockito.when(this._accountRService.getAll()).thenReturn(Arrays.asList(
                this.account1, this.account2, this.account3, this.account4
        ));
        ResponseEntity<?> responseEntity = this._accountRController.getAll();
        Mockito.verify(this._accountRService, Mockito.times(1)).getAll();

        List<Account> list = (List<Account>) responseEntity.getBody();
        MatcherAssert.assertThat(responseEntity.getStatusCodeValue(), Matchers.equalTo(200));
        MatcherAssert.assertThat(list, Matchers.hasSize(4));
        MatcherAssert.assertThat(list.get(0).getId(), Matchers.equalTo(1));
        MatcherAssert.assertThat(list.get(0).getAccountType(), Matchers.equalTo("Ahorro"));
        MatcherAssert.assertThat(list.get(0).getNumber(), Matchers.equalTo("478758"));
        MatcherAssert.assertThat(list.get(1).getId(), Matchers.equalTo(2));
        MatcherAssert.assertThat(list.get(1).getAccountType(), Matchers.equalTo("Corriente"));
        MatcherAssert.assertThat(list.get(1).getNumber(), Matchers.equalTo("225487"));
        MatcherAssert.assertThat(list.get(2).getId(), Matchers.equalTo(3));
        MatcherAssert.assertThat(list.get(2).getAccountType(), Matchers.equalTo("Ahorros"));
        MatcherAssert.assertThat(list.get(2).getNumber(), Matchers.equalTo("495878"));
        MatcherAssert.assertThat(list.get(3).getId(), Matchers.equalTo(4));
        MatcherAssert.assertThat(list.get(3).getAccountType(), Matchers.equalTo("Ahorros"));
        MatcherAssert.assertThat(list.get(3).getNumber(), Matchers.equalTo("496825"));
    }

    @Test
    public void getAllEndpointTest() throws Exception {
        Mockito.when(this._accountRService.getAll()).thenReturn(Arrays.asList(
                this.account1, this.account2, this.account3, this.account4
        ));

        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/cuentas")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(4)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.equalTo(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].number", CoreMatchers.is("478758")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id", Matchers.equalTo(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].number", CoreMatchers.is("225487")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].id", Matchers.equalTo(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].number", CoreMatchers.is("495878")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[3].id", Matchers.equalTo(4)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[3].number", CoreMatchers.is("496825")))
                .andDo(MockMvcResultHandlers.print());

        Mockito.verify(this._accountRService, Mockito.times(1)).getAll();
    }

    @Test
    public void getByIdFunctionTest() {
        Mockito.when(this._accountRService.getById(3)).thenReturn(this.account3);
        ResponseEntity<?> responseEntity = this._accountRController.getById(3);
        Mockito.verify(this._accountRService, Mockito.times(1)).getById(3);

        Account account = (Account) responseEntity.getBody();
        MatcherAssert.assertThat(responseEntity.getStatusCodeValue(), Matchers.equalTo(200));
        MatcherAssert.assertThat(account, CoreMatchers.is(CoreMatchers.notNullValue()));
        MatcherAssert.assertThat(account.getId(), Matchers.equalTo(3));
        MatcherAssert.assertThat(account.getNumber(), Matchers.equalTo("495878"));
        MatcherAssert.assertThat(account.getAccountType(), Matchers.equalTo("Ahorros"));
    }

    @Test
    public void getByIdEndpointTest() throws Exception {
        Mockito.when(this._accountRService.getById(3)).thenReturn(this.account3);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/cuentas/3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", CoreMatchers.is(CoreMatchers.notNullValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.equalTo(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.number", CoreMatchers.is("495878")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.accountType", CoreMatchers.is("Ahorros")))
                .andDo(MockMvcResultHandlers.print());

        Mockito.verify(this._accountRService, Mockito.times(1)).getById(3);
    }
}

