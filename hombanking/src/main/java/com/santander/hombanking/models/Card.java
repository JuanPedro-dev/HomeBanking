package com.santander.hombanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Locale;

@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private Client client;

    private String cardHolder; // Nombre del titular
    private CardColor typeColor;
    private CardType cardType;
    private String number;
    private int cvv;
    private LocalDate fromDate;
    private LocalDate thruDate;

    public Card() {
    }

    public Card(String cardHolder, CardColor typeColor, CardType cardType, String number, int cvv, LocalDate fromDate, LocalDate thruDate) {
        this.cardHolder = cardHolder;
        this.typeColor = typeColor;
        this.cardType = cardType;
        this.number = number;
        this.cvv = cvv;
        this.fromDate = fromDate;
        this.thruDate = thruDate;   // Vencimiento
    }

    public long getId() {
        return id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }

    public CardColor getTypeColor() {
        return typeColor;
    }

    public void setTypeColor(CardColor typeColor) {
        this.typeColor = typeColor;
    }

    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getCvv() {
        return cvv;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getThruDate() {
        return thruDate;
    }

    public void setThruDate(LocalDate thruDate) {
        this.thruDate = thruDate;
    }
}
