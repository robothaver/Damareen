package com.game.damareen.domain;

import com.game.damareen.domain.card.LeadCard;
import com.game.damareen.domain.card.WorldCard;
import com.game.damareen.domain.dungeon.Dungeon;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString
@NoArgsConstructor
public class World {
    private long id;
    private long playerId;
    private String name;
    private List<Dungeon> dungeons;
    private List<WorldCard> worldCards;
    private List<LeadCard> leadCards;

    public World(long id, long playerId, String name, List<Dungeon> dungeons,
                 List<WorldCard> worldCards, List<LeadCard> leadCards) {
        this.id = id;
        this.playerId = playerId;
        this.name = name;
        this.dungeons = dungeons;
        this.worldCards = worldCards;
        this.leadCards = leadCards;
    }
}