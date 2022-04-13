package com.example.bank.dto.read;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

@Data
public class MovementRDto {

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "America/Guayaquil")
    private Date fecha;
    private String client;
    private String numeroCuenta;
    private String tipo;
    private BigDecimal saldoInicial;
    private Boolean estado;
    private BigDecimal movimiento;
    private BigDecimal saldoDisponible;

    public MovementRDto(Object fecha, String client, String numeroCuenta, String tipo, BigDecimal saldoInicial,
                        Boolean estado, BigDecimal movimiento, BigDecimal saldoDisponible) {

        this.fecha = new Date(((Timestamp) fecha).getTime());
        this.client = client;
        this.numeroCuenta = numeroCuenta;
        this.tipo = tipo;
        this.saldoInicial = saldoInicial;
        this.estado = estado;
        this.movimiento = movimiento;
        this.saldoDisponible = saldoDisponible;
    }
}
