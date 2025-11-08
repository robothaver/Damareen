package com.game.damareen.domain;

import lombok.Data;

public class LeadCard extends WorldCard {
    public LeadCard(String cardName, int damage, int health, CardType cardType) {
        super(cardName, damage, health, cardType);
    }
}
