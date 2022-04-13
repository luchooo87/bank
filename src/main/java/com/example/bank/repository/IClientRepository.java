package com.example.bank.repository;

import com.example.bank.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IClientRepository extends JpaRepository<Client, Integer> {

    /**
     * @param status
     * @return List<Client>
     */
    List<Client> findAllByStatusEquals(boolean status);

    /**
     * @param fullName
     * @return Optional<Client>
     */
    Optional<Client> findByFullNameContainingIgnoreCase(String fullName);

}
