package com.example.bank.services;

import com.example.bank.dto.write.AccountWDto;
import com.example.bank.dto.write.MovementWDto;
import com.example.bank.model.Account;
import com.example.bank.model.AccountMovement;
import com.example.bank.model.Client;
import com.example.bank.repository.IAccountRepository;
import com.example.bank.services.impl.AccountRService;
import com.example.bank.services.impl.AccountWService;
import com.example.bank.services.impl.ClientRService;
import com.example.bank.services.impl.MovementWService;
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

@ExtendWith(SpringExtension.class)
public class AccountWServiceTest {

    @Mock
    private IAccountRepository _accountRepository;

    @Mock
    private AccountRService _accountRService;

    @Mock
    private ClientRService _clientRService;

    @Mock
    private MovementWService _movementWService;

    @InjectMocks
    private AccountWService _accountWService;

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
    }

    @Test
    public void createTest() {
        AccountWDto accountWDto = new AccountWDto();
        accountWDto.setAccountType("Ahorro");
        accountWDto.setFullName("Jose Lema");
        accountWDto.setNumber("478758");
        accountWDto.setInitialBalance(new BigDecimal(2000));

        MovementWDto movementWDto = MovementWDto.builder()
                .movementValue(accountWDto.getInitialBalance())
                .numberAccount(accountWDto.getNumber())
                .build();

        AccountMovement movement = new AccountMovement();
        movement.setBalance(movementWDto.getMovementValue());
        movement.setMovementDate(new Timestamp(System.currentTimeMillis()));
        movement.setMovementType("INICIAL");
        movement.setMovementValue(movementWDto.getMovementValue());
        movement.setId(1);
        movement.setAccount(this.account1);

        Mockito.when(this._accountRepository.save(Mockito.any(Account.class))).thenReturn(this.account1);
        Mockito.when(this._movementWService.create(movementWDto)).thenReturn(movement);
        Account account = this._accountWService.create(accountWDto);
        Mockito.verify(this._accountRepository, Mockito.times(1)).save(account);

        MatcherAssert.assertThat(account, CoreMatchers.is(CoreMatchers.notNullValue()));
        MatcherAssert.assertThat(account.getAccountType(), Matchers.equalTo("Ahorro"));
        MatcherAssert.assertThat(account.getNumber(), Matchers.equalTo("478758"));
        MatcherAssert.assertThat(account.getInitialBalance(), Matchers.equalTo(new BigDecimal(2000)));
    }

    @Test
    public void updateTest() {
        AccountWDto accountWDto = new AccountWDto();
        accountWDto.setAccountType("Corriente");
        accountWDto.setFullName("Jose Lema");
        accountWDto.setNumber("478758");
        accountWDto.setInitialBalance(new BigDecimal(50));
        accountWDto.setId(1);

        Account accountUpdated = new Account();
        accountUpdated.setAccountType("Corriente");
        accountUpdated.setInitialBalance(new BigDecimal(50));

        Mockito.when(this._accountRepository.save(Mockito.any(Account.class))).thenReturn(this.account1);
        Mockito.when(this._accountRService.getById(1)).thenReturn(this.account1);

        Account account = this._accountWService.update(accountWDto);
        Mockito.verify(this._accountRepository, Mockito.times(1)).save(account);

        MatcherAssert.assertThat(account, CoreMatchers.is(CoreMatchers.notNullValue()));
        MatcherAssert.assertThat(account.getAccountType(), Matchers.equalTo("Corriente"));
        MatcherAssert.assertThat(account.getNumber(), Matchers.equalTo("478758"));
        MatcherAssert.assertThat(account.getInitialBalance(), Matchers.equalTo(new BigDecimal(50)));
    }

    @Test
    public void deleteTest() {
        Mockito.when(this._accountRService.getById(1)).thenReturn(this.account1);
        Account account = this._accountWService.delete(1);
        Mockito.verify(this._accountRepository, Mockito.times(1)).save(account);

        MatcherAssert.assertThat(account, CoreMatchers.is(CoreMatchers.notNullValue()));
        MatcherAssert.assertThat(account.getStatus(), Matchers.equalTo(false));
    }

}
