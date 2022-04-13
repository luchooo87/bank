package com.example.bank.controller;

import com.example.bank.controller.impl.ClientWController;
import com.example.bank.dto.write.ClientWDto;
import com.example.bank.model.Client;
import com.example.bank.services.impl.ClientWService;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
public class ClientWControllerTest {

    @Mock
    private ClientWService _clientWService;

    @InjectMocks
    private ClientWController _clientWController;

    private MockMvc mockMvc;

    Client client1 = new Client();
    Client client2 = new Client();
    Client client3 = new Client();
    List<Client> clients = new ArrayList<>();

    @BeforeEach
    void initUseCase() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(this._clientWController).build();
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
    public void createFunctionTest() {
        ClientWDto clientWDto = new ClientWDto();
        clientWDto.setAddress("Otavalo sn y principal");
        clientWDto.setPassword("1234");
        clientWDto.setPhone("098254785");
        clientWDto.setFullName("Jose Lema");

        Mockito.when(this._clientWService.create(clientWDto)).thenReturn(this.client1);
        ResponseEntity<?> responseEntity = this._clientWController.create(clientWDto);
        Mockito.verify(this._clientWService, Mockito.times(1)).create(clientWDto);
        MatcherAssert.assertThat(responseEntity.getStatusCodeValue(), Matchers.equalTo(201));
    }

    @Test
    public void createEndpointTest() throws Exception {
        ClientWDto clientWDto = new ClientWDto();
        clientWDto.setAddress("Otavalo sn y principal");
        clientWDto.setPassword("1234");
        clientWDto.setPhone("098254785");
        clientWDto.setFullName("Jose Lema");

        Mockito.when(this._clientWService.create(clientWDto)).thenReturn(this.client1);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(clientWDto);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))

                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print());

        Mockito.verify(this._clientWService, Mockito.times(1)).create(clientWDto);
    }

    @Test
    public void updateFunctionTest() {
        ClientWDto clientWDto = new ClientWDto();
        clientWDto.setId(1);
        clientWDto.setAddress("Otavalo sn y principal modificado");
        clientWDto.setPassword("1234");
        clientWDto.setPhone("098254785");
        clientWDto.setFullName("Jose Lema");

        Mockito.when(this._clientWService.update(clientWDto)).thenReturn(this.client1);
        ResponseEntity<?> responseEntity = this._clientWController.update(clientWDto);
        Mockito.verify(this._clientWService, Mockito.times(1)).update(clientWDto);
        MatcherAssert.assertThat(responseEntity.getStatusCodeValue(), Matchers.equalTo(200));
    }

    @Test
    public void updateEndpointTest() throws Exception {
        ClientWDto clientWDto = new ClientWDto();
        clientWDto.setId(1);
        clientWDto.setAddress("Otavalo sn y principal modificado");
        clientWDto.setPassword("1234");
        clientWDto.setPhone("098254785");
        clientWDto.setFullName("Jose Lema");

        Mockito.when(this._clientWService.update(clientWDto)).thenReturn(this.client1);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(clientWDto);
        this.mockMvc.perform(MockMvcRequestBuilders.put("/api/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

        Mockito.verify(this._clientWService, Mockito.times(1)).update(clientWDto);
    }

    @Test
    public void deleteFunctionTest() {
        Mockito.when(this._clientWService.delete(1)).thenReturn(this.client1);
        ResponseEntity<?> responseEntity = this._clientWController.delete(1);
        Mockito.verify(this._clientWService, Mockito.times(1)).delete(1);
        MatcherAssert.assertThat(responseEntity.getStatusCodeValue(), Matchers.equalTo(200));
    }

    @Test
    public void deleteEndpointTest() throws Exception {
        Mockito.when(this._clientWService.delete(1)).thenReturn(this.client1);
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/api/clientes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
        Mockito.verify(this._clientWService, Mockito.times(1)).delete(1);
    }

}

