package com.game.damareen.service;

import com.game.damareen.db.entity.WorldCardEntity;
import com.game.damareen.db.entity.DungeonEntity;
import com.game.damareen.db.entity.LeadCardEntity;
import com.game.damareen.domain.card.WorldCard;
import com.game.damareen.domain.dungeon.Dungeon;
import com.game.damareen.domain.dungeon.DungeonType;
import com.game.damareen.domain.request.DungeonCreateRequest;
import com.game.damareen.exception.*;
import com.game.damareen.mapper.DungeonMapper;
import com.game.damareen.repository.DungeonRepository;
import com.game.damareen.repository.LeadCardRepository;
import com.game.damareen.repository.PlayerRepository;
import com.game.damareen.repository.WorldCardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class DungeonServiceImpl implements DungeonService {
    private final WorldCardRepository worldCardRepository;
    private final LeadCardRepository leadCardRepository;
    private final PlayerRepository playerRepository;
    private final DungeonRepository dungeonRepository;

    @Override
    @Transactional
    public Dungeon createDungeon(DungeonCreateRequest request, Long playerId) {
        validateDungeonRequest(request);

        DungeonEntity entity = new DungeonEntity();
        entity.setName(request.getName());
        entity.setType(request.getType());
        entity.setPlayer(playerRepository.findById(playerId).orElseThrow(() -> new IllegalStateException("Player not found in card service")));
        List<WorldCardEntity> worldCards = getWorldCards(request.getCards());
        List<WorldCardEntity> cards = new ArrayList<>(worldCards);
        if (request.getType().requiresLeader()) {
            LeadCardEntity leaderEntity = leadCardRepository.findById(request.getLeadCard()).orElseThrow(() -> new CardNotFoundException("Leader card not found: " + request.getLeadCard()));
            cards.add(new WorldCardEntity(leaderEntity.getId(), leaderEntity.getPlayer(), leaderEntity.getCardName(), leaderEntity.getDamage(), leaderEntity.getHealth(), leaderEntity.getCardType()));
        }
        entity.setCards(cards);

        return DungeonMapper.fromEntity(dungeonRepository.save(entity));
    }

    @Override
    public Dungeon getDungeonById(Long id, Long playerId) {
        DungeonEntity dungeonEntity = dungeonRepository
                .findAllByPlayer_Id(playerId).stream()
                .filter(dungeon -> dungeon.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new DungeonNotFoundException("Dungeon not found: " + id));

        return DungeonMapper.fromEntity(dungeonEntity);
    }

    @Override
    public List<Dungeon> getDungeons(Long playerId) {
        return dungeonRepository
                .findAllByPlayer_Id(playerId).stream()
                .map(DungeonMapper::fromEntity)
                .toList();
    }

    private void validateDungeonRequest(DungeonCreateRequest request) {
        DungeonType type = request.getType();

        if (dungeonRepository.existsByName(request.getName())) {
            throw new DuplicateDungeonNameException("A dungeon with the same name already exits!");
        }

        int expectedRegularCards = type.getRegularCardCount();
        int actualCards = request.getCards().size();

        if (actualCards != expectedRegularCards) {
            throw new InvalidDungeonStructureException(
                    String.format("Dungeon type %s requires %d regular cards, but got %d",
                            type, expectedRegularCards, actualCards));
        }

        if (type.requiresLeader() && request.getLeadCard() == null) {
            throw new InvalidDungeonStructureException(
                    String.format("Dungeon type %s requires a leader card", type));
        }

        if (!type.requiresLeader() && request.getLeadCard() != null) {
            throw new InvalidDungeonStructureException(
                    String.format("Dungeon type %s does not use leader cards", type));
        }
    }

    private List<WorldCardEntity> getWorldCards(List<Long> ids) {
        Set<Long> idSet = new HashSet<>();
        for (Long id : ids) {
            if (!idSet.add(id)) {
                throw new DuplicateCardsInDungeonException("Duplicate cards found in dungeon: " + id);
            }
        }
        return idSet.stream()
                .map(id -> worldCardRepository.findById(id)
                        .orElseThrow(() -> new CardNotFoundException("Card not found: " + id)))
                .toList();
    }
}