package com.example.bank.model;

import com.example.bank.views.Views;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;


/**
 * The persistent class for the account_movements database table.
 */
@Entity
@Table(name = "account_movements")
@NamedQuery(name = "AccountMovement.findAll", query = "SELECT a FROM AccountMovement a")
public class AccountMovement implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    @JsonView({ Views.Keys.class })
    private Integer id;

    @Column(nullable = false, precision = 10, scale = 6)
    @JsonView({ Views.Read.class, Views.Write.class })
    private BigDecimal balance;

    @Column(name = "movement_date", nullable = false)
    @JsonView({ Views.Read.class, Views.Write.class })
    private Timestamp movementDate;

    @Column(name = "movement_type", nullable = false, length = 10)
    @JsonView({ Views.Read.class, Views.Write.class })
    private String movementType;

    @Column(name = "movement_value", precision = 10, scale = 6)
    @JsonView({ Views.Read.class, Views.Write.class })
    private BigDecimal movementValue;

    @JsonView({ Views.Read.class, Views.Write.class })
    private Boolean status;

    //bi-directional many-to-one association to Account
    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    @JsonView({ Views.Read.class, Views.Write.class })
    private Account account;

    public AccountMovement() {
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getBalance() {
        return this.balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Timestamp getMovementDate() {
        return this.movementDate;
    }

    public void setMovementDate(Timestamp movementDate) {
        this.movementDate = movementDate;
    }

    public String getMovementType() {
        return this.movementType;
    }

    public void setMovementType(String movementType) {
        this.movementType = movementType;
    }

    public BigDecimal getMovementValue() {
        return this.movementValue;
    }

    public void setMovementValue(BigDecimal movementValue) {
        this.movementValue = movementValue;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Account getAccount() {
        return this.account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

}