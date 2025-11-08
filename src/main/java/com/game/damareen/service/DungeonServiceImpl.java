package com.game.damareen.service;

import com.game.damareen.domain.card.LeadCard;
import com.game.damareen.domain.card.WorldCard;
import com.game.damareen.db.entity.CardEntity;
import com.game.damareen.db.entity.DungeonEntity;
import com.game.damareen.db.entity.LeaderCardEntity;
import com.game.damareen.db.mapper.CardMapper;
import com.game.damareen.domain.dungeon.Dungeon;
import com.game.damareen.domain.dungeon.DungeonType;
import com.game.damareen.domain.request.DungeonCreateRequest;
import com.game.damareen.exception.CardNotFoundException;
import com.game.damareen.exception.DuplicateCardsInDungeonException;
import com.game.damareen.exception.DungeonNotFoundException;
import com.game.damareen.exception.InvalidDungeonStructureException;
import com.game.damareen.repository.DungeonRepository;
import com.game.damareen.repository.LeadCardRepository;
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
    private final DungeonRepository dungeonRepository;
    private final CardMapper mapper;

    @Override
    @Transactional
    public Dungeon createDungeon(DungeonCreateRequest request) {
        validateDungeonRequest(request);

        List<WorldCard> worldCards = getWorldCards(request.getCards());
        LeadCard leadCard = getLeadCard(request.getLeadCard());

        DungeonEntity entity = new DungeonEntity(request.getName(), request.getType());

        List<CardEntity> cardEntities = request.getCards().stream()
                .map(id -> worldCardRepository.findById(id)
                        .orElseThrow(() -> new CardNotFoundException("Card not found: " + id)))
                .toList();
        entity.setCards(cardEntities);

        if (request.getType().requiresLeader()) {
            CardEntity leaderEntity = leadCardRepository.findById(request.getLeadCard())
                    .orElseThrow(() -> new CardNotFoundException("Leader card not found: " + request.getLeadCard()));
            entity.getCards().add(leaderEntity);
        }

        dungeonRepository.save(entity);

        return new Dungeon(request.getName(), request.getType(), worldCards, leadCard);
    }

    @Override
    public Dungeon getDungeonById(Long id) {
        DungeonEntity entity = dungeonRepository.findById(id)
                .orElseThrow(() -> new DungeonNotFoundException("Dungeon not found: " + id));

        List<WorldCard> worldCards = new ArrayList<>();
        LeadCard leadCard = null;

        for (CardEntity card : entity.getCards()) {
            if (card instanceof LeaderCardEntity) {
                leadCard = new LeadCard(
                        card.getId(),
                        card.getCardName(),
                        card.getDamage(),
                        card.getHealth(),
                        card.getCardType()
                );
            } else {
                worldCards.add(mapper.fromEntity(card));
            }
        }

        return new Dungeon(entity.getName(), entity.getType(), worldCards, leadCard);
    }

    private void validateDungeonRequest(DungeonCreateRequest request) {
        DungeonType type = request.getType();

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

    private List<WorldCard> getWorldCards(List<Long> ids) {
        Set<Long> idSet = new HashSet<>();
        for (Long id : ids) {
            if (!idSet.add(id)) {
                throw new DuplicateCardsInDungeonException("Duplicate cards found in dungeon: " + id);
            }
        }
        return idSet.stream()
                .map(id -> worldCardRepository.findById(id)
                        .orElseThrow(() -> new CardNotFoundException("Card not found: " + id)))
                .map(mapper::fromEntity)
                .toList();
    }

    private LeadCard getLeadCard(Long id) {
        if (id == null) return null;

        CardEntity entity = leadCardRepository.findById(id)
                .orElseThrow(() -> new CardNotFoundException("Leader card not found: " + id));

        return new LeadCard(
                entity.getId(),
                entity.getCardName(),
                entity.getDamage(),
                entity.getHealth(),
                entity.getCardType()
        );
    }
}