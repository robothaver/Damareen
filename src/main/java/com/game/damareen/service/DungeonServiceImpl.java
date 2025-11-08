package com.game.damareen.service;

import com.game.damareen.domain.card.LeadCard;
import com.game.damareen.domain.card.WorldCard;
import com.game.damareen.domain.db.entity.CardEntity;
import com.game.damareen.domain.db.mapper.CardMapper;
import com.game.damareen.domain.dungeon.Dungeon;
import com.game.damareen.domain.request.DungeonCreateRequest;
import com.game.damareen.exception.DuplicateCardsInDungeonException;
import com.game.damareen.repository.LeadCardRepository;
import com.game.damareen.repository.WorldCardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class DungeonServiceImpl implements DungeonService {
    private final WorldCardRepository worldCardRepository;
    private final LeadCardRepository leadCardRepository;
    private final CardMapper mapper;

    @Override
    public Dungeon createDungeon(DungeonCreateRequest request) {
        List<WorldCard> worldCards = getWorldCards(request.getCards());
        Optional<CardEntity> leadCardEntity = leadCardRepository.findById(request.getLeadCard());
        if (leadCardEntity.isEmpty()) throw new RuntimeException("Failed to get lead card from db!");
        LeadCard leadCard = (LeadCard) mapper.fromEntity(leadCardEntity.get());
        return new Dungeon(request.getName(), request.getType(), worldCards, leadCard);
    }

    private List<WorldCard> getWorldCards(List<Long> ids) {
        Set<Long> idSet = new HashSet<>();
        for (Long id : ids) {
            if (!idSet.add(id)) {
                throw new DuplicateCardsInDungeonException("Duplicate cards found in dungeon create request!");
            }
        }
        return idSet.stream()
                .map(worldCardRepository::getReferenceById)
                .map(mapper::fromEntity)
                .toList();
    }
}
