package com.example.bank.dto.write;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientWDto {

    private Integer id;
    private String address;
    private String fullName;
    private String password;
    private String phone;
    private Integer age;
    private String identification;

}
