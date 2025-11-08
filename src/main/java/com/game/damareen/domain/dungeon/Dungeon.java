package com.game.damareen.domain.dungeon;

import com.game.damareen.domain.card.LeadCard;
import com.game.damareen.domain.card.WorldCard;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class Dungeon {
    private String name;
    private DungeonType type;
    private List<WorldCard> worldCards;
    private LeadCard leadCard;

    public Dungeon() {
    }

    public Dungeon(String name, DungeonType type, List<WorldCard> worldCards, LeadCard leadCard) {
        this.name = name;
        this.type = type;
        this.worldCards = worldCards;
        this.leadCard = leadCard;
    }

    @Override
    public String toString() {
        return "Dungeon{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", worldCards=" + worldCards +
                ", leadCard=" + leadCard +
                '}';
    }
}