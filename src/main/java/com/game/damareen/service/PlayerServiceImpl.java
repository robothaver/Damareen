package com.game.damareen.service;

import com.game.damareen.domain.db.entity.PlayerEntity;
import com.game.damareen.exception.DuplicatePlayerException;
import com.game.damareen.exception.PlayerNotFoundException;
import com.game.damareen.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PlayerServiceImpl implements PlayerService {
    private final PlayerRepository playerRepository;

    @Override
    @Transactional
    public void createPlayer(String userName) {
        if (playerRepository.existsByUserName(userName)) {
            throw new DuplicatePlayerException("Player with username '" + userName + "' already exists");
        }
        PlayerEntity player = new PlayerEntity(userName);
        playerRepository.save(player);
    }

    @Override
    public long getPlayer(String userName) {
        return playerRepository.findByUserName(userName)
                .orElseThrow(() -> new PlayerNotFoundException("Player '" + userName + "' not found"))
                .getId();
    }
}