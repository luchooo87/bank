package com.example.bank.controller;

import com.example.bank.controller.impl.ClientRController;
import com.example.bank.model.Client;
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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
public class ClientRControllerTest {

    @Mock
    private ClientRService _clientRService;

    @InjectMocks
    private ClientRController _clientRController;

    private MockMvc mockMvc;

    Client client1 = new Client();
    Client client2 = new Client();
    Client client3 = new Client();
    List<Client> clients = new ArrayList<>();

    @BeforeEach
    void initUseCase() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(this._clientRController).build();
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
    public void getAllFunctionTest() {
        Mockito.when(this._clientRService.getAll()).thenReturn(Arrays.asList(
                this.client1, this.client2
        ));
        ResponseEntity<?> responseEntity = this._clientRController.getAll();
        Mockito.verify(this._clientRService, Mockito.times(1)).getAll();

        List<Client> list = (List<Client>) responseEntity.getBody();
        MatcherAssert.assertThat(responseEntity.getStatusCodeValue(), Matchers.equalTo(200));
        MatcherAssert.assertThat(list, Matchers.hasSize(2));
        MatcherAssert.assertThat(list.get(0).getId(), Matchers.equalTo(1));
        MatcherAssert.assertThat(list.get(1).getAddress(), Matchers.equalTo("Amazonas y NNUU"));
    }

    @Test
    public void getAllEndpointTest() throws Exception {
        Mockito.when(this._clientRService.getAll()).thenReturn(Arrays.asList(
                this.client1, this.client2
        ));

        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/clientes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.equalTo(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].fullName", CoreMatchers.is("Jose Lema")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].fullName", CoreMatchers.is("Marianela Montalvo")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].identification", Matchers.equalTo("123456789")))
                .andDo(MockMvcResultHandlers.print());

        Mockito.verify(this._clientRService, Mockito.times(1)).getAll();
    }

    @Test
    public void getByIdFunctionTest() {
        Mockito.when(this._clientRService.getById(3)).thenReturn(this.client3);
        ResponseEntity<?> responseEntity = this._clientRController.getById(3);
        Mockito.verify(this._clientRService, Mockito.times(1)).getById(3);

        Client client = (Client) responseEntity.getBody();
        MatcherAssert.assertThat(responseEntity.getStatusCodeValue(), Matchers.equalTo(200));
        MatcherAssert.assertThat(client, CoreMatchers.is(CoreMatchers.notNullValue()));
        MatcherAssert.assertThat(client.getId(), Matchers.equalTo(3));
        MatcherAssert.assertThat(client.getFullName(), Matchers.equalTo("Juan Osorio"));
    }

    @Test
    public void getByIdEndpointTest() throws Exception {
        Mockito.when(this._clientRService.getById(3)).thenReturn(this.client3);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/clientes/3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", CoreMatchers.is(CoreMatchers.notNullValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.equalTo(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fullName", CoreMatchers.is("Juan Osorio")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.identification", CoreMatchers.is("10036787")))
                .andDo(MockMvcResultHandlers.print());

        Mockito.verify(this._clientRService, Mockito.times(1)).getById(3);
    }
}

