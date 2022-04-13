package com.example.bank.repository;

import com.example.bank.dto.read.MovementRDto;
import com.example.bank.model.AccountMovement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface IAccountMovementsRepository extends JpaRepository<AccountMovement, Integer> {

    /**
     * @param starDate
     * @param endDate
     * @param accountNumber
     * @return List<AccountMovement>
     */
    List<AccountMovement> findByMovementDateBetweenAndAccount_Number(Date starDate, Date endDate, String accountNumber);

    /**
     * @param accountNumber
     * @return List<AccountMovement>
     */
    List<AccountMovement> findByAccount_Number(String accountNumber);

    /**
     * @param startDate
     * @param endDate
     * @param accountNumber
     * @return List<MovementRDto>
     */
    @Query("SELECT new com.example.bank.dto.read.MovementRDto(movement.movementDate, movement.account.client.fullName," +
            "movement.account.number, movement.movementType, movement.account.initialBalance, movement.status, " +
            "movement.movementValue, movement.balance) " +
            "FROM AccountMovement movement " +
            "WHERE movement.account.number = :accountNumber AND movement.movementDate BETWEEN :startDate AND :endDate")
    List<MovementRDto> generateReport(@Param("startDate") Date startDate, @Param("endDate") Date endDate,
                                      @Param("accountNumber") String accountNumber);

}
