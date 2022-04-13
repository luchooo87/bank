package com.example.bank.model;

import com.example.bank.views.Views;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the account database table.
 */
@Entity
@Table(name = "account")
@NamedQuery(name = "Account.findAll", query = "SELECT a FROM Account a")
public class Account implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    @JsonView({ Views.Keys.class })
    private Integer id;

    @Column(name = "account_type", nullable = false, length = 20)
    @JsonView({ Views.Read.class, Views.Write.class })
    private String accountType;

    @Column(name = "initial_balance", nullable = false, precision = 10, scale = 6)
    @JsonView({ Views.Read.class, Views.Write.class })
    private BigDecimal initialBalance;

    @Column(nullable = false, length = 20)
    @JsonView({ Views.Read.class, Views.Write.class })
    private String number;

    @Column(nullable = false)
    @JsonView({ Views.Read.class, Views.Write.class })
    private Boolean status;

    //bi-directional many-to-one association to Client
    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    @JsonView({ Views.Read.class, Views.Write.class })
    private Client client;

    //bi-directional many-to-one association to AccountMovement
    @OneToMany(mappedBy = "account")
    private List<AccountMovement> accountMovements;

    public Account() {
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccountType() {
        return this.accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public BigDecimal getInitialBalance() {
        return this.initialBalance;
    }

    public void setInitialBalance(BigDecimal initialBalance) {
        this.initialBalance = initialBalance;
    }

    public String getNumber() {
        return this.number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Boolean getStatus() {
        return this.status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Client getClient() {
        return this.client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public List<AccountMovement> getAccountMovements() {
        return this.accountMovements;
    }

    public void setAccountMovements(List<AccountMovement> accountMovements) {
        this.accountMovements = accountMovements;
    }

    public AccountMovement addAccountMovement(AccountMovement accountMovement) {
        getAccountMovements().add(accountMovement);
        accountMovement.setAccount(this);

        return accountMovement;
    }

    public AccountMovement removeAccountMovement(AccountMovement accountMovement) {
        getAccountMovements().remove(accountMovement);
        accountMovement.setAccount(null);

        return accountMovement;
    }

}