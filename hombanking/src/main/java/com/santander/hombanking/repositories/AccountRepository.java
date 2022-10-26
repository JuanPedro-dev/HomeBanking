package com.santander.hombanking.repositories;


import com.santander.hombanking.models.Account;
import com.santander.hombanking.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query("SELECT account FROM Account account")
    List<Account> finAllAccount();

    @Query("SELECT account FROM Account account WHERE account.id = ?1")
    Account findBYId(long id);


}
