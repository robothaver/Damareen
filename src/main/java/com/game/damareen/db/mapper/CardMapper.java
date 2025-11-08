package com.game.damareen.db.mapper;

import com.game.damareen.domain.card.WorldCard;
import com.game.damareen.db.entity.CardEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CardMapper {

    WorldCard fromEntity(CardEntity entity);
    CardEntity toEntity(WorldCard card);
}
