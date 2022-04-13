package com.example.bank.dto.write;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovementWDto {

    private String numberAccount;
    private String typeMovement;
    private BigDecimal movementValue;

}
