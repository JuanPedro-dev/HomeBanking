package com.santander.hombanking.dtos;

import com.santander.hombanking.models.Card;
import com.santander.hombanking.models.CardColor;
import com.santander.hombanking.models.CardType;

import java.time.LocalDate;

public class CardDTO {
    private long id;
    private String cardHolder; // Nombre del titular
    private CardColor color;
    private CardType type;
    private String number;
    private int cvv;
    private LocalDate fromDate;
    private LocalDate thruDate;

    public CardDTO(Card card) {
        this.id = card.getId();
        this.cardHolder = card.getCardHolder();
        this.color = card.getTypeColor();
        this.type = card.getCardType();
        this.number = card.getNumber();
        this.cvv = card.getCvv();
        this.fromDate = card.getFromDate();
        this.thruDate = card.getThruDate();
    }

    public long getId() {
        return id;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public CardColor getColor() {
        return color;
    }

    public CardType getType() {
        return type;
    }

    public String getNumber() {
        return number;
    }

    public int getCvv() {
        return cvv;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public LocalDate getThruDate() {
        return thruDate;
    }
}
