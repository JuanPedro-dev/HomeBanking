package com.santander.hombanking.controllers;

import com.santander.hombanking.models.*;
import com.santander.hombanking.repositories.AccountRepository;
import com.santander.hombanking.repositories.CardRepository;
import com.santander.hombanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.Set;


@RestController
@RequestMapping(value = "/api")
public class CardController {

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    CardRepository cardRepository;

    @PostMapping(value = "/clients/current/accounts")
    public ResponseEntity<Object> createAccount(Authentication authentication){
        Client clientOnSession =  clientRepository.findByEmail(authentication.getName()).orElse(null);
        assert clientOnSession != null;
        Set<Account> accounts = clientOnSession.getAccounts();

        if(accounts.size() >= 3){
            return new ResponseEntity<>("Can´t create another card (limited exided)", HttpStatus.FORBIDDEN);
        } else {

            // Creo una cuenta
//            int numero = new Random().nextInt(100000000);
            int numero = (int)(Math.random()*(-99999999 +1)+99999999);
            String numero8Caracteres = String.format("%0" + 8 + "d", numero);
            String number = "VIN-"+ numero8Caracteres;
            Account accountToAdd =  new Account(number, LocalDateTime.now(), 0, clientOnSession);

            // Le agrego al cliente la cuenta
            clientOnSession.addAccount(accountToAdd);

            clientRepository.save(clientOnSession);
            accountRepository.save(accountToAdd);
        }

        return new ResponseEntity<>("Account created sucessfuly.", HttpStatus.CREATED);
    }

    @PostMapping(value = "/clients/current/cards")
    public ResponseEntity<Object> createCard(@RequestParam CardType cardType,
                                             @RequestParam CardColor cardColor, Authentication authentication){

        Client client = clientRepository.findByEmail(authentication.getName()).orElse(null);

        // Continuar hacer que si tienen 3 tarjetas de ese tipo no las agregue...
        assert client != null;
        Set<Card> cards = client.getCards();

        int countType = 0;
        for (Card card : cards) {
            if(card.getCardType() == cardType){
                countType++;
            }
        }

        if(countType >= 3){
            return new ResponseEntity<>("Cant create more cards (limited exided)", HttpStatus.FORBIDDEN);
        }

        // Creo un numero para la cuenta: 3333-XXXX-XXXX-XXXX
//        int numero = (int)(Math.random()*(-99999999 +1)+99999999); // Opcion 2 para crear un número random
        int numero = new Random().nextInt(10000);
        String digitos4Generados = String.format("%0" + 4 + "d", numero);
        String number = "3333-"+ digitos4Generados;

        numero = new Random().nextInt(10000);
        digitos4Generados = String.format("%0" + 4 + "d", numero);
        number += "-"+ digitos4Generados;

        numero = new Random().nextInt(10000);
        digitos4Generados = String.format("%0" + 4 + "d", numero);
        number += "-"+ digitos4Generados;

        // Creo un numero para la cuenta: 3333-XXXX-XXXX-XXXX
//        int numero = (int)(Math.random()*(-99999999 +1)+99999999); // Opcion 2 para crear un número random
        numero = new Random().nextInt(1000);
        String tresDigitos = String.format("%0" + 3 + "d", numero);
        int cvv = Integer.parseInt(tresDigitos);

        Card cardToAdd = new Card(client.getFirstName() + " " + client.getLastName(), cardColor, cardType, number, cvv, LocalDate.now(), LocalDate.now().plusYears(5));

        client.addCard(cardToAdd);

        cardRepository.save(cardToAdd);
        clientRepository.save(client);

        return new ResponseEntity<>("Created card sucessfuly.", HttpStatus.CREATED);

    }
}
