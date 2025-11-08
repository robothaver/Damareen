package com.game.damareen.domain.card;

public enum CardType {
    FIRE,
    WATER,
    AIR,
    EARTH;

    public boolean beats(CardType other) {
        return switch (this) {
            case FIRE -> other == EARTH;
            case WATER -> other == AIR;
            case AIR -> other == FIRE;
            case EARTH -> other == WATER;
        };
    }
}
