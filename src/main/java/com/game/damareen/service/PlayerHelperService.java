package com.game.damareen.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlayerHelperService {
    private final PlayerService playerService;

    public long createPlayerIfNotExists(String userName) {
        Long playerId = playerService.getPlayerId(userName);
        if (playerId == null) {
            playerId = playerService.createPlayer(userName);
        }
        return playerId;
    }
}
