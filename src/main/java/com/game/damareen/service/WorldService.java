package com.game.damareen.service;

import com.game.damareen.domain.World;

public interface WorldService {
    World createWorld(long playerId);
    World getWorldById(long worldId);
}
