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
public class AccountWDto {

    private Integer id;
    private String accountType;
    private BigDecimal initialBalance;
    private String number;
    private Integer clientId;
    private String fullName;

}
