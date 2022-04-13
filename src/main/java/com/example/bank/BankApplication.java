package com.example.bank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@EnableTransactionManagement
@SpringBootApplication(scanBasePackages = { BankApplication.PACKAGE_NAME + ".config",
        BankApplication.PACKAGE_NAME + ".controller.*", BankApplication.PACKAGE_NAME + ".services.*",
        BankApplication.PACKAGE_NAME + ".error" })
@EntityScan(basePackages = { BankApplication.PACKAGE_NAME + ".model" })
@EnableJpaRepositories(basePackages = { BankApplication.PACKAGE_NAME + ".repository" })
public class BankApplication {

    public final static String PACKAGE_NAME = "com.example.bank";

    public static void main(String[] args) {
        SpringApplication.run(BankApplication.class, args);
    }

}
