package com.game.damareen.mapper;

import com.game.damareen.db.entity.LeadCardEntity;
import com.game.damareen.db.entity.PlayerEntity;
import com.game.damareen.db.entity.WorldCardEntity;
import com.game.damareen.domain.card.LeadCard;
import com.game.damareen.domain.card.WorldCard;

public class CardMapper {
    public static WorldCard fromEntity(WorldCardEntity entity) {
        return new WorldCard(entity.getId(), entity.getCardName(), entity.getDamage(), entity.getHealth(), entity.getCardType());
    }

    public static WorldCardEntity toEntity(WorldCard card, PlayerEntity player) {
        return new WorldCardEntity(card.getId(), player, card.getCardName(), card.getDamage(), card.getHealth(), card.getCardType());
    }

    public static LeadCard fromEntity(LeadCardEntity entity) {
        return new LeadCard(entity.getId(), entity.getCardName(), entity.getDamage(), entity.getHealth(), entity.getCardType());
    }

    public static LeadCardEntity toEntity(LeadCard card, PlayerEntity player) {
        return new LeadCardEntity(card.getId(), player, card.getCardName(), card.getDamage(), card.getHealth(), card.getCardType());
    }
}
