package com.game.damareen.domain.dungeon;

import com.game.damareen.domain.card.LeadCard;
import com.game.damareen.domain.card.WorldCard;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Dungeon {
    private long id;
    private String name;
    private DungeonType type;
    private List<WorldCard> worldCards;
    private LeadCard leadCard;
}