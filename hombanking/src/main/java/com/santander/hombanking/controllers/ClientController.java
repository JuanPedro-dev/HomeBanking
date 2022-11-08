package com.santander.hombanking.controllers;

import com.santander.hombanking.dtos.AccountDTO;
import com.santander.hombanking.dtos.ClientDTO;
import com.santander.hombanking.dtos.TransactionDTO;
import com.santander.hombanking.models.Client;
import com.santander.hombanking.repositories.AccountRepository;
import com.santander.hombanking.repositories.ClientRepository;
import com.santander.hombanking.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;


@RestController
@RequestMapping(value = "/api")
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

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

    @PostMapping(value = "/clients")
    public ResponseEntity<Object> register(
            @RequestParam String firstName, @RequestParam String lastName,
            @RequestParam String email, @RequestParam String password
    ){
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }

        if (clientRepository.findByEmail(email).orElse(null) !=  null) {
            return new ResponseEntity<>("Name already in use", HttpStatus.FORBIDDEN);
        }

        clientRepository.save(new Client(firstName, lastName, email, passwordEncoder.encode(password)));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @RequestMapping("/clients/current")
    public ClientDTO getClientCurrent(Authentication authentication){
        return clientRepository.findByEmail(authentication.getName()).map(ClientDTO::new).orElse(null);
    }

}
