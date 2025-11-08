package com.game.damareen.domain.card;

import lombok.Data;

@Data
public class WorldCard {
    protected final long id;
    protected final String cardName;
    protected final int damage;
    protected final int health;
    protected final CardType cardType;
}