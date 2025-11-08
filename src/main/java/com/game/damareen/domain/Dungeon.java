package com.game.damareen.domain;

import lombok.Data;

import java.util.List;

@Data
public class Dungeon {
    private final DungeonType type;
    private List<WorldCard> cards;
}
