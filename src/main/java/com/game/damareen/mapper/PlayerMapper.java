package com.game.damareen.mapper;

import com.game.damareen.db.entity.PlayerEntity;
import com.game.damareen.domain.Player;

public class PlayerMapper {
    public static Player fromEntity(PlayerEntity entity) {
        return new Player(entity.getId(), entity.getUserName(), entity.getCreatedAt());
    }

    public static PlayerEntity toEntity(Player player) {
        return new PlayerEntity(player.getId(), player.getUserName(), player.getCreationDate());
    }
}
