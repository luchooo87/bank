package com.example.bank.services;

import com.example.bank.model.Client;
import com.example.bank.repository.IClientRepository;
import com.example.bank.services.impl.ClientRService;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class ClientRServiceTest {

    @Mock
    private IClientRepository _clientRepository;

    @InjectMocks
    private ClientRService _clientRService;

    Client client1 = new Client();
    Client client2 = new Client();
    Client client3 = new Client();
    List<Client> clients = new ArrayList<>();

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

        this.clients = Arrays.asList(
                this.client1, this.client2, this.client3
        );
    }

    @Test
    public void getByIdTest() {
        Mockito.when(this._clientRepository.findById(1)).thenReturn(Optional.of(this.client1));
        Client client = this._clientRService.getById(1);
        Mockito.verify(this._clientRepository, Mockito.times(1)).findById(1);

        MatcherAssert.assertThat(client, CoreMatchers.is(CoreMatchers.notNullValue()));
        MatcherAssert.assertThat(client.getId(), Matchers.equalTo(1));
        MatcherAssert.assertThat(client.getFullName(), Matchers.equalTo("Jose Lema"));
        MatcherAssert.assertThat(client.getStatus(), Matchers.equalTo(true));
        MatcherAssert.assertThat(client.getPassword(), Matchers.equalTo("1234"));
    }

    @Test
    public void getAllTest() {
        Mockito.when(this._clientRepository.findAllByStatusEquals(true)).thenReturn(Arrays.asList(
                this.client1, this.client2
        ));
        List<Client> result = this._clientRService.getAll();
        Mockito.verify(this._clientRepository, Mockito.times(1)).findAllByStatusEquals(true);

        MatcherAssert.assertThat(result, Matchers.hasSize(2));
        MatcherAssert.assertThat(result.get(0).getId(), Matchers.equalTo(1));
        MatcherAssert.assertThat(result.get(0).getFullName(), Matchers.equalTo("Jose Lema"));
        MatcherAssert.assertThat(result.get(1).getStatus(), Matchers.equalTo(true));
        MatcherAssert.assertThat(result.get(1).getFullName(), Matchers.equalTo("Marianela Montalvo"));
    }

    @Test
    public void getByFullNameTest() {
        Mockito.when(this._clientRepository.findByFullNameContainingIgnoreCase("marianela")).thenReturn(Optional.of(this.client2));
        Client client = this._clientRService.getByFullName("marianela");
        Mockito.verify(this._clientRepository, Mockito.times(1))
                .findByFullNameContainingIgnoreCase("marianela");

        MatcherAssert.assertThat(client, CoreMatchers.is(CoreMatchers.notNullValue()));
        MatcherAssert.assertThat(client.getId(), Matchers.equalTo(2));
        MatcherAssert.assertThat(client.getFullName(), Matchers.equalTo("Marianela Montalvo"));
        MatcherAssert.assertThat(client.getStatus(), Matchers.equalTo(true));
        MatcherAssert.assertThat(client.getPassword(), Matchers.equalTo("5678"));
    }
}
