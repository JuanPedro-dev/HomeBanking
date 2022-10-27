package com.santander.hombanking.controllers;

import com.santander.hombanking.dtos.AccountDTO;
import com.santander.hombanking.dtos.ClientDTO;
import com.santander.hombanking.dtos.TransactionDTO;
import com.santander.hombanking.models.Client;
import com.santander.hombanking.repositories.AccountRepository;
import com.santander.hombanking.repositories.ClientRepository;
import com.santander.hombanking.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;


@RestController
@RequestMapping(value = "/api")
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;

    @GetMapping(value = "/clients")
    public List<ClientDTO> getClients(){
        return clientRepository.findAll().stream().map(client -> new ClientDTO(client)).collect(toList());

    }

    @GetMapping(value = "/clients/{id}")
    public ClientDTO getClient(@PathVariable long id){
        return clientRepository.findById(id).map(ClientDTO::new).orElse(null);
    }

    @GetMapping(value = "/clientes/{email}")
    public ClientDTO getClient(@PathVariable String email){
        return clientRepository.findByEmail(email).map(ClientDTO::new).orElse(null);
    }

}
