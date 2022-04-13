package com.example.bank.repository;

import com.example.bank.dto.read.MovementRDto;
import com.example.bank.model.Account;
import com.example.bank.model.AccountMovement;
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
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@ExtendWith(SpringExtension.class)
public class IAccountMovementsRepositoryTest {

    @Mock
    private IAccountMovementsRepository _accountMovementsRepository;

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    AccountMovement movement1 = new AccountMovement();
    AccountMovement movement2 = new AccountMovement();
    AccountMovement movement3 = new AccountMovement();
    List<MovementRDto> report = new ArrayList<>();

    Account account1 = new Account();
    Account account2 = new Account();
    Account account3 = new Account();
    Account account4 = new Account();
    Account account5 = new Account();

    Client client1 = new Client();
    Client client2 = new Client();
    Client client3 = new Client();

    @BeforeEach
    void initUseCase() throws ParseException {
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

        this.movement1.setId(1);
        this.movement1.setAccount(this.account1);
        this.movement1.setMovementValue(new BigDecimal(575));
        this.movement1.setMovementType("DEPOSITO");
        this.movement1.setMovementDate(Timestamp.valueOf("2022-04-01 12:34:56"));
        this.movement1.setBalance(new BigDecimal(575));
        this.movement1.setStatus(true);

        this.movement2.setId(2);
        this.movement2.setAccount(this.account1);
        this.movement2.setMovementValue(new BigDecimal(-10));
        this.movement2.setMovementType("RETIRO");
        this.movement2.setMovementDate(Timestamp.valueOf("2022-04-02 16:40:05"));
        this.movement2.setBalance(new BigDecimal(565));
        this.movement2.setStatus(true);

        this.movement3.setId(3);
        this.movement3.setAccount(this.account2);
        this.movement3.setMovementValue(new BigDecimal(45));
        this.movement3.setMovementType("DEPOSITO");
        this.movement3.setMovementDate(Timestamp.valueOf("2022-04-02 16:43:34"));
        this.movement3.setBalance(new BigDecimal(45));
        this.movement3.setStatus(true);

        MovementRDto movementRDto1 = new MovementRDto(Timestamp.valueOf("2022-04-01 12:34:56")
                , "Jose Lema", "478758", "DEPOSITO", new BigDecimal(2000), true,
                new BigDecimal(575), new BigDecimal(2575));

        MovementRDto movementRDto2 = new MovementRDto(Timestamp.valueOf("2022-04-07 09:21:04"),
                "Jose Lema", "478758", "RETIRO", new BigDecimal(2000), true, new BigDecimal(-100),
                new BigDecimal(2475));
        this.report = Arrays.asList(movementRDto1, movementRDto2);
    }

    @Test
    public void findByMovementDateBetweenAndAccount_NumberTest() throws ParseException {
        Date startDate = this.dateFormat.parse("2022-04-01 00:00:00");
        Date endDate = this.dateFormat.parse("2022-04-07 23:59:29");
        Mockito.when(this._accountMovementsRepository.findByMovementDateBetweenAndAccount_Number
                (startDate, endDate, "478758")).thenReturn(Arrays.asList(
                this.movement1, this.movement2
        ));
        List<AccountMovement> result = this._accountMovementsRepository.findByMovementDateBetweenAndAccount_Number
                (startDate, endDate, "478758");

        Mockito.verify(this._accountMovementsRepository, Mockito.times(1))
                .findByMovementDateBetweenAndAccount_Number(startDate, endDate, "478758");

        MatcherAssert.assertThat(result, Matchers.hasSize(2));
        MatcherAssert.assertThat(result.get(0).getId(), Matchers.equalTo(1));
        MatcherAssert.assertThat(result.get(0).getAccount().getNumber(), Matchers.equalTo("478758"));
        MatcherAssert.assertThat(result.get(0).getBalance(), Matchers.equalTo(new BigDecimal(575)));

        MatcherAssert.assertThat(result.get(1).getId(), Matchers.equalTo(2));
        MatcherAssert.assertThat(result.get(1).getMovementDate(), Matchers.equalTo(Timestamp.valueOf("2022-04-02 16:40:05")));
        MatcherAssert.assertThat(result.get(1).getMovementType(), Matchers.equalTo("RETIRO"));
    }

    @Test
    public void findByMovementDateBetweenAndAccount_NumberNotFoundTest() throws ParseException {
        Date startDate = this.dateFormat.parse("2022-04-15 00:00:00");
        Date endDate = this.dateFormat.parse("2022-04-18 23:59:29");
        Mockito.when(this._accountMovementsRepository.findByMovementDateBetweenAndAccount_Number
                (startDate, endDate, "478758")).thenReturn(Collections.emptyList());

        List<AccountMovement> result = this._accountMovementsRepository.findByMovementDateBetweenAndAccount_Number
                (startDate, endDate, "478758");

        Mockito.verify(this._accountMovementsRepository, Mockito.times(1))
                .findByMovementDateBetweenAndAccount_Number(startDate, endDate, "478758");

        MatcherAssert.assertThat(result, Matchers.hasSize(0));
    }

    @Test
    public void findByAccount_NumberTest() {
        Mockito.when(this._accountMovementsRepository.findByAccount_Number("225487"))
                .thenReturn(Arrays.asList(this.movement3));

        List<AccountMovement> result = this._accountMovementsRepository.findByAccount_Number("225487");

        Mockito.verify(this._accountMovementsRepository, Mockito.times(1))
                .findByAccount_Number("225487");

        MatcherAssert.assertThat(result, Matchers.hasSize(1));
        MatcherAssert.assertThat(result.get(0).getId(), Matchers.equalTo(3));
        MatcherAssert.assertThat(result.get(0).getAccount().getNumber(), Matchers.equalTo("225487"));
        MatcherAssert.assertThat(result.get(0).getBalance(), Matchers.equalTo(new BigDecimal(45)));
    }

    @Test
    public void findByAccount_NumberNotFoundTest() {
        Mockito.when(this._accountMovementsRepository.findByAccount_Number("124")).thenReturn(Collections.emptyList());
        List<AccountMovement> result = this._accountMovementsRepository.findByAccount_Number("124");

        Mockito.verify(this._accountMovementsRepository, Mockito.times(1))
                .findByAccount_Number("124");

        MatcherAssert.assertThat(result, Matchers.hasSize(0));
    }

    @Test
    public void generateReportTest() throws ParseException {
        Date startDate = this.dateFormat.parse("2022-04-01 00:00:00");
        Date endDate = this.dateFormat.parse("2022-04-10 23:59:29");
        Mockito.when(this._accountMovementsRepository.generateReport(startDate, endDate, "478758")).thenReturn(this.report);

        List<MovementRDto> result = this._accountMovementsRepository.generateReport(startDate, endDate, "478758");

        Mockito.verify(this._accountMovementsRepository, Mockito.times(1))
                .generateReport(startDate, endDate, "478758");
        ;
        MatcherAssert.assertThat(result, Matchers.hasSize(2));
        MatcherAssert.assertThat(result.get(0).getNumeroCuenta(), Matchers.equalTo("478758"));
        MatcherAssert.assertThat(result.get(0).getTipo(), Matchers.equalTo("DEPOSITO"));

        MatcherAssert.assertThat(result.get(1).getMovimiento(), Matchers.equalTo(new BigDecimal(-100)));
        MatcherAssert.assertThat(result.get(1).getSaldoDisponible(), Matchers.equalTo(new BigDecimal(2475)));
    }

}
