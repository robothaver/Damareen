package com.game.damareen.service;

import com.game.damareen.db.entity.LeadCardEntity;
import com.game.damareen.db.entity.WorldCardEntity;
import com.game.damareen.mapper.CardMapper;
import com.game.damareen.domain.card.CardDerivation;
import com.game.damareen.domain.card.LeadCard;
import com.game.damareen.domain.card.WorldCard;
import com.game.damareen.domain.request.CreateLeadCardRequest;
import com.game.damareen.domain.request.WorldCardCreateRequest;
import com.game.damareen.repository.LeadCardRepository;
import com.game.damareen.repository.PlayerRepository;
import com.game.damareen.repository.WorldCardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {
    private final WorldCardRepository worldCardRepository;
    private final LeadCardRepository leadCardRepository;
    private final PlayerRepository playerRepository;

    @Override
    public WorldCard createWorldCard(WorldCardCreateRequest request, long playerId) {
        WorldCardEntity worldCardEntity = WorldCardEntity.builder()
                .player(playerRepository.findById(playerId).orElseThrow(() -> new IllegalStateException("Player not found in card service")))
                .cardName(request.getName())
                .damage(request.getDamage())
                .health(request.getHealth())
                .cardType(request.getCardType())
                .build();
        worldCardRepository.save(worldCardEntity);
        return CardMapper.fromEntity(worldCardEntity);
    }

    @Override
    public LeadCard enhaceCard(CreateLeadCardRequest request, long playerId) {
        WorldCard worldCard = worldCardRepository.findById(request.getWorldCardId()).map(CardMapper::fromEntity).orElseThrow();

        LeadCardEntity leadCardEntity = LeadCardEntity.builder()
                .player(playerRepository.findById(playerId).orElseThrow(() -> new IllegalStateException("Player not found in card service")))
                .cardName(request.getName())
                .damage(request.getDerivation() == CardDerivation.DOUBLE_DAMAGE ? worldCard.getDamage() * 2 : worldCard.getDamage())
                .health(request.getDerivation() == CardDerivation.DOUBLE_HEALTH ? worldCard.getHealth() * 2 : worldCard.getHealth())
                .cardType(worldCard.getCardType())
                .build();
        leadCardRepository.save(leadCardEntity);
        return CardMapper.fromEntity(leadCardEntity);
    }

    @Override
    public List<WorldCard> getWorldCards(long playerId) {
        return worldCardRepository.
                findAllByPlayer_Id(playerId).stream()
                .map(CardMapper::fromEntity)
                .toList();
    }

    @Override
    public List<LeadCard> getLeadCards(long playerId) {
        return leadCardRepository
                .findAllByPlayer_Id(playerId).stream()
                .map(CardMapper::fromEntity)
                .toList();
    }
}
