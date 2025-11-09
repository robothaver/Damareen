package com.game.damareen.service;

import com.game.damareen.db.entity.PlayerEntity;
import com.game.damareen.domain.Player;
import com.game.damareen.exception.DuplicatePlayerException;
import com.game.damareen.mapper.PlayerMapper;
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
    public long createPlayer(String userName) {
        if (playerRepository.existsByUserName(userName)) {
            throw new DuplicatePlayerException("Player with username '" + userName + "' already exists");
        }
        PlayerEntity player = new PlayerEntity(userName);
        PlayerEntity save = playerRepository.save(player);
        return save.getId();
    }

    @Override
    public Long getPlayerId(String userName) {
        return playerRepository
                .findByUserName(userName)
                .map(PlayerEntity::getId)
                .orElse(null);
    }

    @Override
    public Player getPlayerById(Long id) {
        return playerRepository
                .findById(id)
                .map(PlayerMapper::fromEntity)
                .orElseThrow(() -> new IllegalArgumentException("Player not found!"));
    }
}