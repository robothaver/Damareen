package com.game.damareen.service;

import com.game.damareen.domain.dungeon.Dungeon;
import com.game.damareen.domain.request.DungeonCreateRequest;

public interface DungeonService {
    Dungeon createDungeon(DungeonCreateRequest request);
    Dungeon getDungeonById(Long id);
}