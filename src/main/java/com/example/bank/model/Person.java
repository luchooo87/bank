package com.example.bank.model;

import com.example.bank.views.Views;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import java.io.Serializable;


/**
 * The persistent class for the person database table.
 */
@Entity
@Table(name = "person")
@NamedQuery(name = "Person.findAll", query = "SELECT p FROM Person p")
@Inheritance(strategy = InheritanceType.JOINED)
public class Person implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    @JsonView({ Views.Keys.class })
    private Integer id;

    @JsonView({ Views.Read.class, Views.Write.class })
    @Column(length = 200)
    private String address;

    @JsonView({ Views.Read.class, Views.Write.class })
    private Integer age;

    @Column(name = "full_name", nullable = false, length = 100)
    @JsonView({ Views.Read.class, Views.Write.class })
    private String fullName;

    @Column(length = 20)
    @JsonView({ Views.Read.class, Views.Write.class })
    private String identification;

    @Column(length = 20)
    @JsonView({ Views.Read.class, Views.Write.class })
    private String phone;

    //bi-directional one-to-one association to Client
    @OneToOne(mappedBy = "person")
    private Client client;

    public Person() {
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getAge() {
        return this.age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getFullName() {
        return this.fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getIdentification() {
        return this.identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Client getClient() {
        return this.client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

}