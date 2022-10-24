package com.santander.hombanking.repositories;


import com.santander.hombanking.models.Account;
import com.santander.hombanking.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface AccountRepository extends JpaRepository<Account, Long> {


}
