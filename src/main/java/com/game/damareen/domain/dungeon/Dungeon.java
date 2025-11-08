package com.game.damareen.domain.dungeon;

import com.game.damareen.domain.card.LeadCard;
import com.game.damareen.domain.card.WorldCard;
import lombok.Data;

import java.util.List;

@Data
public class Dungeon {
    private final String name;
    private final DungeonType type;
    private final List<WorldCard> worldCards;
    private final LeadCard leadCard;
}
