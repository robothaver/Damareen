package com.game.damareen.domain.card;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorldCard {
    protected long id;
    protected String cardName;
    protected int damage;
    protected int health;
    protected CardType cardType;
}