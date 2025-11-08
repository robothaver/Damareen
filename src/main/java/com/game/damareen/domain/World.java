package com.game.damareen.domain;

import com.game.damareen.domain.card.LeadCard;
import com.game.damareen.domain.card.WorldCard;
import com.game.damareen.domain.dungeon.Dungeon;
import lombok.Data;

import java.util.List;

@Data
public class World {
    private final long id;
    private final long playerId;
    private final String name;
    private final List<Dungeon> dungeons;
    private final List<WorldCard> worldCards;
    private final List<LeadCard> leadCards;
}
