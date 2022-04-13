package com.example.bank.repository;

import com.example.bank.model.Client;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

@ExtendWith(SpringExtension.class)
public class IClientRepositoryTest {

    @Mock
    private IClientRepository _clientRepository;

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
    public void findAllByStatusEqualsTrueTest() {
        Mockito.when(this._clientRepository.findAllByStatusEquals(true)).thenReturn(Arrays.asList(
                this.client1, this.client2
        ));
        List<Client> result = this._clientRepository.findAllByStatusEquals(true);
        Mockito.verify(this._clientRepository, Mockito.times(1)).findAllByStatusEquals(true);

        MatcherAssert.assertThat(result, Matchers.hasSize(2));
        MatcherAssert.assertThat(result.get(0).getId(), Matchers.equalTo(1));
        MatcherAssert.assertThat(result.get(0).getFullName(), Matchers.equalTo("Jose Lema"));
        MatcherAssert.assertThat(result.get(1).getStatus(), Matchers.equalTo(true));
        MatcherAssert.assertThat(result.get(1).getFullName(), Matchers.equalTo("Marianela Montalvo"));
    }

    @Test
    public void findAllByStatusEqualsFalseTest() {
        Mockito.when(this._clientRepository.findAllByStatusEquals(false)).thenReturn(Arrays.asList(
                this.client3
        ));
        List<Client> result = this._clientRepository.findAllByStatusEquals(false);
        Mockito.verify(this._clientRepository, Mockito.times(1)).findAllByStatusEquals(false);

        MatcherAssert.assertThat(result, Matchers.hasSize(1));
        MatcherAssert.assertThat(result.get(0).getStatus(), Matchers.equalTo(false));
    }

    @Test
    public void findAllByStatusEqualsEmptyTest() {
        Mockito.when(this._clientRepository.findAllByStatusEquals(true)).thenReturn(Collections.emptyList());
        List<Client> result = this._clientRepository.findAllByStatusEquals(true);
        Mockito.verify(this._clientRepository, Mockito.times(1)).findAllByStatusEquals(true);

        MatcherAssert.assertThat(result, Matchers.hasSize(0));
    }

    @Test
    public void findByFullNameContainingIgnoreCaseTest() {
        Mockito.when(this._clientRepository.findByFullNameContainingIgnoreCase("jose")).thenReturn(Optional.of(this.client1));
        Optional<Client> result = this._clientRepository.findByFullNameContainingIgnoreCase("jose");
        Mockito.verify(this._clientRepository, Mockito.times(1)).findByFullNameContainingIgnoreCase("jose");

        MatcherAssert.assertThat(result.isPresent(), Matchers.equalTo(true));
        MatcherAssert.assertThat(result.get().getFullName(), Matchers.equalTo("Jose Lema"));
    }

    @Test
    public void findByFullNameContainingIgnoreCaseNotFoundTest() {
        Mockito.when(this._clientRepository.findByFullNameContainingIgnoreCase("juan")).thenReturn(Optional.empty());
        Optional<Client> result = this._clientRepository.findByFullNameContainingIgnoreCase("juan");
        Mockito.verify(this._clientRepository, Mockito.times(1)).findByFullNameContainingIgnoreCase("juan");

        MatcherAssert.assertThat(result.isPresent(), Matchers.equalTo(false));
    }

}
