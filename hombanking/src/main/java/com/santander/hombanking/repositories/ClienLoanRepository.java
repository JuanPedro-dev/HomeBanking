package com.santander.hombanking.repositories;

import com.santander.hombanking.models.ClientLoan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ClienLoanRepository extends JpaRepository<ClientLoan, Long> {
}
