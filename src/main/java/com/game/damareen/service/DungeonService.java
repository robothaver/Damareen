package com.game.damareen.service;

import com.game.damareen.domain.dungeon.Dungeon;
import com.game.damareen.domain.request.DungeonCreateRequest;

import java.util.List;

public interface DungeonService {
    Dungeon createDungeon(DungeonCreateRequest request, Long playerId);
    Dungeon getDungeonById(Long id, Long playerId);
    List<Dungeon> getDungeons(Long playerId);
}