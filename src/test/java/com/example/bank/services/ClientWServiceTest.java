package com.example.bank.services;

import com.example.bank.dto.write.ClientWDto;
import com.example.bank.model.Client;
import com.example.bank.repository.IClientRepository;
import com.example.bank.services.impl.ClientRService;
import com.example.bank.services.impl.ClientWService;
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

@ExtendWith(SpringExtension.class)
public class ClientWServiceTest {

    @Mock
    private IClientRepository _clientRepository;

    @Mock
    private ClientRService _clientRService;

    @InjectMocks
    private ClientWService _clientWService;

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
    }

    @Test
    public void createTest() {
        ClientWDto clientWDto = new ClientWDto();
        clientWDto.setAddress("Otavalo sn y principal");
        clientWDto.setPassword("1234");
        clientWDto.setPhone("098254785");
        clientWDto.setFullName("Jose Lema");

        Mockito.when(this._clientRepository.save(Mockito.any(Client.class))).thenReturn(this.client1);
        Client client = this._clientWService.create(clientWDto);
        Mockito.verify(this._clientRepository, Mockito.times(1)).save(client);
        MatcherAssert.assertThat(client, CoreMatchers.is(CoreMatchers.notNullValue()));
        MatcherAssert.assertThat(client.getFullName(), Matchers.equalTo("Jose Lema"));
        MatcherAssert.assertThat(client.getStatus(), Matchers.equalTo(true));
        MatcherAssert.assertThat(client.getPassword(), Matchers.equalTo("1234"));
    }

    @Test
    public void upateTest() {
        ClientWDto clientWDto = new ClientWDto();
        clientWDto.setId(1);
        clientWDto.setAddress("Otavalo sn y principal modificado");
        clientWDto.setPassword("11111");
        clientWDto.setPhone("098254785");
        clientWDto.setFullName("Jose Lema Aguilar");

        Client clientUpdated = new Client();
        clientUpdated.setAddress("Otavalo sn y principal modificado");
        clientUpdated.setFullName("Jose Lema Aguilar");
        clientUpdated.setPassword("11111");

        Mockito.when(this._clientRService.getById(1)).thenReturn(this.client1);
        Client client = this._clientWService.update(clientWDto);
        Mockito.verify(this._clientRService, Mockito.times(1)).getById(1);
        Mockito.verify(this._clientRepository, Mockito.times(1)).save(client);

        MatcherAssert.assertThat(client, CoreMatchers.is(CoreMatchers.notNullValue()));
        MatcherAssert.assertThat(client.getFullName(), Matchers.equalTo("Jose Lema Aguilar"));
        MatcherAssert.assertThat(client.getAddress(), Matchers.equalTo("Otavalo sn y principal modificado"));
        MatcherAssert.assertThat(client.getPassword(), Matchers.equalTo("11111"));
    }

    @Test
    public void deleteTest() {
        Mockito.when(this._clientRService.getById(1)).thenReturn(this.client1);
        Client client = this._clientWService.delete(1);
        Mockito.verify(this._clientRService, Mockito.times(1)).getById(1);
        Mockito.verify(this._clientRepository, Mockito.times(1)).save(client);

        MatcherAssert.assertThat(client, CoreMatchers.is(CoreMatchers.notNullValue()));
        MatcherAssert.assertThat(client.getStatus(), Matchers.equalTo(false));
    }

}
