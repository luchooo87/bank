package com.example.bank.services;

import com.example.bank.dto.write.MovementWDto;
import com.example.bank.model.Account;
import com.example.bank.model.AccountMovement;
import com.example.bank.model.Client;
import com.example.bank.repository.IAccountMovementsRepository;
import com.example.bank.services.impl.*;
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
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.sql.Timestamp;

@ExtendWith(SpringExtension.class)
public class MovementWServiceTest {

    @Mock
    private IAccountMovementsRepository _accountMovementsRepository;

    @Mock
    private AccountRService _accountRService;

    @Mock
    private ClientRService _clientRService;

    @Mock
    private AccountWService _accountWService;

    @Mock
    private MovementRService _movementRService;

    @InjectMocks
    private MovementWService _movementWService;

    AccountMovement movement1 = new AccountMovement();
    Account account1 = new Account();
    Client client1 = new Client();

    @BeforeEach
    void initUseCase() {
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
    public void createTest() {
        MovementWDto movementWDto = new MovementWDto();
        movementWDto.setMovementValue(new BigDecimal(100));
        movementWDto.setTypeMovement("DEPOSITO");
        movementWDto.setNumberAccount("478758");
        Mockito.when(this._accountMovementsRepository.save(Mockito.any(AccountMovement.class))).thenReturn(this.movement1);
        AccountMovement movement = this._movementWService.create(movementWDto);

        Mockito.verify(this._accountMovementsRepository, Mockito.times(1)).save(movement);
        MatcherAssert.assertThat(movement, CoreMatchers.is(CoreMatchers.notNullValue()));
        MatcherAssert.assertThat(movement.getBalance(), Matchers.equalTo(new BigDecimal(100)));
        MatcherAssert.assertThat(movement.getMovementValue(), Matchers.equalTo(new BigDecimal(100)));
        MatcherAssert.assertThat(movement.getMovementType(), Matchers.equalTo("DEPOSITO"));
    }

    @Test
    public void createFirstMovementTest() {
        MovementWDto movementWDto = new MovementWDto();
        movementWDto.setMovementValue(new BigDecimal(50));
        movementWDto.setTypeMovement("INICIAL");
        movementWDto.setNumberAccount("478758");

        AccountMovement movementSaved = new AccountMovement();
        movementSaved.setMovementType("INICIAL");
        movementSaved.setMovementValue(new BigDecimal(50));
        movementSaved.setBalance(new BigDecimal(50));
        movementSaved.setMovementDate(Timestamp.valueOf("2022-04-07 09:21:04"));
        movementSaved.setAccount(this.account1);
        movementSaved.setStatus(true);
        movementSaved.setId(1);
        Mockito.when(this._accountMovementsRepository.save(Mockito.any(AccountMovement.class))).thenReturn(movementSaved);
        AccountMovement movement = this._movementWService.create(movementWDto);

        Mockito.verify(this._accountMovementsRepository, Mockito.times(1)).save(movement);
        MatcherAssert.assertThat(movement, CoreMatchers.is(CoreMatchers.notNullValue()));
        MatcherAssert.assertThat(movement.getBalance(), Matchers.equalTo(new BigDecimal(50)));
        MatcherAssert.assertThat(movement.getMovementValue(), Matchers.equalTo(new BigDecimal(50)));
        MatcherAssert.assertThat(movement.getMovementType(), Matchers.equalTo("INICIAL"));
    }

    @Test
    public void deleteTest() {
        Mockito.when(this._movementRService.getById(1)).thenReturn(this.movement1);
        AccountMovement movement = this._movementWService.delete(1);
        Mockito.verify(this._accountMovementsRepository, Mockito.times(1)).save(movement);

        MatcherAssert.assertThat(movement, CoreMatchers.is(CoreMatchers.notNullValue()));
        MatcherAssert.assertThat(movement.getStatus(), Matchers.equalTo(false));
    }

}
