package com.game.damareen.service;

import com.game.damareen.domain.Player;

public interface PlayerService {
    long createPlayer(String userName);
    Long getPlayerId(String userName);
    Player getPlayerById(Long id);
}
