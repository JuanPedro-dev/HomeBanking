package com.santander.hombanking.repositories;


import com.santander.hombanking.models.Account;
import com.santander.hombanking.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByNumber(String number);
    List<Account> findByClient(Client client);

    @Query("SELECT account FROM Account account")
    List<Account> findAllAccounts_JPQL();

    @Query("SELECT account FROM Account account WHERE account.id = ?1")
    Optional<Account> findById_JPQL(long id);


}
