package com.game.damareen.domain.card;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class WorldCard {
    protected long id;
    protected String cardName;
    protected int damage;
    protected int health;
    protected CardType cardType;

    public WorldCard() {
    }

    public WorldCard(long id, String cardName, int damage, int health, CardType cardType) {
        this.id = id;
        this.cardName = cardName;
        this.damage = damage;
        this.health = health;
        this.cardType = cardType;
    }

    @Override
    public String toString() {
        return "WorldCard{" +
                "id=" + id +
                ", cardName='" + cardName + '\'' +
                ", damage=" + damage +
                ", health=" + health +
                ", cardType=" + cardType +
                '}';
    }
}