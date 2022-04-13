package com.example.bank.model;

import com.example.bank.views.Views;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


/**
 * The persistent class for the client database table.
 */
@Entity
@Table(name = "client")
@NamedQuery(name = "Client.findAll", query = "SELECT c FROM Client c")
@PrimaryKeyJoinColumn(referencedColumnName = "id")
public class Client extends Person implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(nullable = false, length = 50)
    @JsonView({ Views.Read.class, Views.Write.class })
    private String password;

    @JsonView({ Views.Read.class, Views.Write.class })
    private Boolean status;

    //bi-directional many-to-one association to Account
    @OneToMany(mappedBy = "client")
    private List<Account> accounts;

    //bi-directional one-to-one association to Person
    @OneToOne
    @JoinColumn(name = "id", nullable = false, insertable = false, updatable = false)
    private Person person;

    public Client() {
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getStatus() {
        return this.status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<Account> getAccounts() {
        return this.accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public Account addAccount(Account account) {
        getAccounts().add(account);
        account.setClient(this);

        return account;
    }

    public Account removeAccount(Account account) {
        getAccounts().remove(account);
        account.setClient(null);

        return account;
    }

    public Person getPerson() {
        return this.person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

}