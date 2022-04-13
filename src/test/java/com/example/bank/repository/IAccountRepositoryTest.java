package com.example.bank.repository;

import com.example.bank.model.Account;
import com.example.bank.model.Client;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.*;

@ExtendWith(SpringExtension.class)
public class IAccountRepositoryTest {

    @Mock
    private IAccountRepository _accountRepository;

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
    public void findAllByStatusEqualsTrueTest() {
        Mockito.when(this._accountRepository.findAllByStatusEquals(true)).thenReturn(this.accounts);
        List<Account> result = this._accountRepository.findAllByStatusEquals(true);
        Mockito.verify(this._accountRepository, Mockito.times(1)).findAllByStatusEquals(true);

        MatcherAssert.assertThat(result, Matchers.hasSize(4));
        MatcherAssert.assertThat(result.get(0).getId(), Matchers.equalTo(1));
        MatcherAssert.assertThat(result.get(0).getNumber(), Matchers.equalTo("478758"));
        MatcherAssert.assertThat(result.get(3).getStatus(), Matchers.equalTo(true));
        MatcherAssert.assertThat(result.get(2).getAccountType(), Matchers.equalTo("Ahorros"));
    }

    @Test
    public void findAllByStatusEqualsFalseTest() {
        Mockito.when(this._accountRepository.findAllByStatusEquals(false)).thenReturn(Arrays.asList(
                this.account5
        ));
        List<Account> result = this._accountRepository.findAllByStatusEquals(false);
        Mockito.verify(this._accountRepository, Mockito.times(1)).findAllByStatusEquals(false);

        MatcherAssert.assertThat(result, Matchers.hasSize(1));
        MatcherAssert.assertThat(result.get(0).getStatus(), Matchers.equalTo(false));
        MatcherAssert.assertThat(result.get(0).getInitialBalance(), Matchers.equalTo(new BigDecimal(540)));
    }

    @Test
    public void findAllByStatusEqualsEmptyTest() {
        Mockito.when(this._accountRepository.findAllByStatusEquals(true)).thenReturn(Collections.emptyList());
        List<Account> result = this._accountRepository.findAllByStatusEquals(true);
        Mockito.verify(this._accountRepository, Mockito.times(1)).findAllByStatusEquals(true);

        MatcherAssert.assertThat(result, Matchers.hasSize(0));
    }

    @Test
    public void findByNumberTest() {
        Mockito.when(this._accountRepository.findByNumber("496825")).thenReturn(Optional.of(this.account4));
        Optional<Account> result = this._accountRepository.findByNumber("496825");

        Mockito.verify(this._accountRepository, Mockito.times(1)).findByNumber("496825");
        MatcherAssert.assertThat(result.isPresent(), Matchers.equalTo(true));
        MatcherAssert.assertThat(result.get().getId(), Matchers.equalTo(4));
        MatcherAssert.assertThat(result.get().getNumber(), Matchers.equalTo("496825"));
    }

    @Test
    public void findByNumberNotFoundTest() {
        Mockito.when(this._accountRepository.findByNumber("123")).thenReturn(Optional.empty());
        Optional<Account> result = this._accountRepository.findByNumber("123");
        MatcherAssert.assertThat(result.isPresent(), Matchers.equalTo(false));
    }

}
