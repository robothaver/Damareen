package com.game.damareen.domain;

import lombok.Data;

@Data
public class WorldCard {
    protected final String cardName;
    protected final int damage;
    protected final int health;
    protected final CardType cardType;
}