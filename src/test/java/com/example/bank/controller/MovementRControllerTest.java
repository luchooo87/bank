package com.example.bank.controller;

import com.example.bank.controller.impl.MovementRController;
import com.example.bank.dto.read.MovementRDto;
import com.example.bank.services.impl.MovementRService;
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
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
public class MovementRControllerTest {

    @Mock
    private MovementRService _movementRService;

    @InjectMocks
    private MovementRController _movementRController;

    private MockMvc mockMvc;

    MovementRDto movementRDto1;

    MovementRDto movementRDto2;

    List<MovementRDto> report = new ArrayList<>();

    @BeforeEach
    void initUseCase() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(this._movementRController).build();

        this.movementRDto1 = new MovementRDto(Timestamp.valueOf("2022-04-01 12:34:56")
                , "Jose Lema", "478758", "DEPOSITO", new BigDecimal(2000), true,
                new BigDecimal(575), new BigDecimal(2575));

        this.movementRDto2 = new MovementRDto(Timestamp.valueOf("2022-04-07 09:21:04"),
                "Jose Lema", "478758", "RETIRO", new BigDecimal(2000), true, new BigDecimal(-100),
                new BigDecimal(2475));

        this.report = Arrays.asList(movementRDto1, movementRDto2);
    }

    @Test
    public void getAllFunctionTest() throws ParseException {
        Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse("2022-04-01");
        Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse("2022-04-12");

        Mockito.when(this._movementRService.getReport(startDate, endDate, "478758")).thenReturn(this.report);
        ResponseEntity<?> responseEntity = this._movementRController.getAll("478758", startDate, endDate);
        Mockito.verify(this._movementRService, Mockito.times(1)).getReport(startDate, endDate, "478758");

        List<MovementRDto> result = (List<MovementRDto>) responseEntity.getBody();
        MatcherAssert.assertThat(responseEntity.getStatusCodeValue(), Matchers.equalTo(200));
        MatcherAssert.assertThat(result, Matchers.hasSize(2));
        MatcherAssert.assertThat(result, Matchers.hasSize(2));
        MatcherAssert.assertThat(result.get(0).getSaldoDisponible(), Matchers.equalTo(new BigDecimal(2575)));
        MatcherAssert.assertThat(result.get(0).getTipo(), Matchers.equalTo("DEPOSITO"));
        MatcherAssert.assertThat(result.get(0).getClient(), Matchers.equalTo("Jose Lema"));
        MatcherAssert.assertThat(result.get(0).getFecha(), Matchers.equalTo(Timestamp.valueOf("2022-04-01 12:34:56")));

        MatcherAssert.assertThat(result.get(1).getSaldoDisponible(), Matchers.equalTo(new BigDecimal(2475)));
        MatcherAssert.assertThat(result.get(1).getTipo(), Matchers.equalTo("RETIRO"));
        MatcherAssert.assertThat(result.get(1).getMovimiento(), Matchers.equalTo(new BigDecimal(-100)));
        MatcherAssert.assertThat(result.get(1).getFecha(), Matchers.equalTo(Timestamp.valueOf("2022-04-07 09:21:04")));
    }

    @Test
    public void getAllEndpointTest() throws Exception {
        Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse("2022-04-01");
        Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse("2022-04-12");

        Mockito.when(this._movementRService.getReport(startDate, endDate, "478758")).thenReturn(this.report);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/movimientos")
                        .param("account", "478758")
                        .param("startDate", "2022-04-01")
                        .param("endDate", "2022-04-12")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].saldoDisponible", Matchers.equalTo(2575)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].tipo", CoreMatchers.is("DEPOSITO")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].client", Matchers.equalTo("Jose Lema")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].fecha", CoreMatchers.is("2022-04-01 12:34:56")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].saldoDisponible", Matchers.equalTo(2475)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].tipo", CoreMatchers.is("RETIRO")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].movimiento", Matchers.equalTo(-100)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].fecha", CoreMatchers.is("2022-04-07 09:21:04")))
                .andDo(MockMvcResultHandlers.print());

        Mockito.verify(this._movementRService, Mockito.times(1)).getReport(startDate, endDate, "478758");
    }

}

