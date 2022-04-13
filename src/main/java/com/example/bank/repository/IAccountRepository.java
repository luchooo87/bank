package com.example.bank.repository;

import com.example.bank.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IAccountRepository extends JpaRepository<Account, Integer> {

    /**
     * @param status
     * @return List<Account>
     */
    List<Account> findAllByStatusEquals(boolean status);

    /**
     * @param number
     * @return Optional<Account>
     */
    Optional<Account> findByNumber(String number);

}
