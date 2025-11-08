package com.game.damareen.service;

import com.game.damareen.domain.World;
import com.game.damareen.domain.db.mapper.WorldMapper;
import com.game.damareen.repository.WorldCardRepository;
import com.game.damareen.repository.WorldRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WorldServiceImpl implements WorldService {
    private final WorldRepository worldRepository;
    private final WorldCardRepository cardRepository;
    private final WorldMapper mapper;

    @Override
    public World createWorld(long playerId) {
        return null;
    }

    @Override
    public World getWorldById(long worldId) {
        return mapper.fromEntity(worldRepository.getReferenceById(worldId));
    }
}
