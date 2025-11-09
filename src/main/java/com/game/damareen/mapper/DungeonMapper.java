package com.game.damareen.mapper;

import com.game.damareen.db.entity.DungeonEntity;
import com.game.damareen.db.entity.WorldCardEntity;
import com.game.damareen.domain.card.LeadCard;
import com.game.damareen.domain.card.WorldCard;
import com.game.damareen.domain.dungeon.Dungeon;
import com.game.damareen.domain.dungeon.DungeonType;

import java.util.ArrayList;
import java.util.List;

public class DungeonMapper {
    public static Dungeon fromEntity(DungeonEntity entity) {
        System.out.println(entity);
        LeadCard leadCard = null;
        if (entity.getType() != DungeonType.SIMPLE_ENCOUNTER) {
            WorldCard worldCard = CardMapper.fromEntity(entity.getCards().get(entity.getCards().size() - 1));
            leadCard = new LeadCard(worldCard.getId(), worldCard.getCardName(), worldCard.getDamage(), worldCard.getHealth(), worldCard.getCardType());
        }
        List<WorldCard> worldCards = new ArrayList<>();
        for (int i = 0; i < entity.getCards().size(); i++) {
            WorldCard worldCard = CardMapper.fromEntity(entity.getCards().get(i));
            if (entity.getType() == DungeonType.SIMPLE_ENCOUNTER || i != entity.getCards().size() - 1) {
                worldCards.add(worldCard);
            }
        }
        return new Dungeon(
                entity.getId(),
                entity.getName(),
                entity.getType(),
                worldCards,
                leadCard
        );
    }
}
