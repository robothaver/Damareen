package com.game.damareen.service;

import com.game.damareen.domain.World;
import com.game.damareen.domain.card.CardDerivation;
import com.game.damareen.domain.card.CardType;
import com.game.damareen.domain.card.LeadCard;
import com.game.damareen.domain.card.WorldCard;
import com.game.damareen.domain.db.entity.CardEntity;
import com.game.damareen.domain.db.entity.LeaderCardEntity;
import com.game.damareen.domain.db.entity.WorldEntity;
import com.game.damareen.domain.db.mapper.CardMapper;
import com.game.damareen.exception.CardNotFoundException;
import com.game.damareen.exception.WorldNotFoundException;
import com.game.damareen.repository.WorldCardRepository;
import com.game.damareen.repository.WorldRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WorldServiceImpl implements WorldService {
    private final WorldRepository worldRepository;
    private final WorldCardRepository cardRepository;
    private final CardMapper mapper;

    @Override
    @Transactional
    public World createWorld(String name, List<WorldCard> cards, List<LeadCard> leaders) {
        WorldEntity entity = new WorldEntity(name);

        List<CardEntity> cardEntities = cards.stream()
                .map(mapper::toEntity)
                .collect(Collectors.toList());
        entity.setWorldCards(cardEntities);

        List<LeaderCardEntity> leaderEntities = leaders.stream()
                .map(this::toLeaderEntity)
                .collect(Collectors.toList());
        entity.setLeaderCards(leaderEntities);

        WorldEntity saved = worldRepository.save(entity);

        return mapToWorld(saved);
    }

    @Override
    public World getWorldById(long worldId) {
        WorldEntity entity = worldRepository.findById(worldId)
                .orElseThrow(() -> new WorldNotFoundException("World not found: " + worldId));
        return mapToWorld(entity);
    }

    @Override
    @Transactional
    public WorldCard addWorldCard(long worldId, String name, int damage, int health, CardType type) {
        WorldEntity world = worldRepository.findById(worldId)
                .orElseThrow(() -> new WorldNotFoundException("World not found: " + worldId));

        CardEntity card = new CardEntity(null, name, damage, health, type);
        world.getWorldCards().add(card);
        worldRepository.save(world);

        return mapper.fromEntity(card);
    }

    @Override
    @Transactional
    public LeadCard createLeaderCard(long worldId, String baseCardName, String leaderName, CardDerivation derivation) {
        WorldEntity world = worldRepository.findById(worldId)
                .orElseThrow(() -> new WorldNotFoundException("World not found: " + worldId));

        CardEntity baseCard = world.getWorldCards().stream()
                .filter(c -> c.getCardName().equals(baseCardName))
                .findFirst()
                .orElseThrow(() -> new CardNotFoundException("Base card not found: " + baseCardName));

        int damage = baseCard.getDamage();
        int health = baseCard.getHealth();

        if (derivation == CardDerivation.DOUBLE_DAMAGE) {
            damage *= 2;
        } else if (derivation == CardDerivation.DOUBLE_HEALTH) {
            health *= 2;
        }

        LeaderCardEntity leader = new LeaderCardEntity();
        leader.setCardName(leaderName);
        leader.setDamage(damage);
        leader.setHealth(health);
        leader.setCardType(baseCard.getCardType());
        leader.setBaseCardName(baseCardName);
        leader.setDerivationType(derivation);

        world.getLeaderCards().add(leader);
        worldRepository.save(world);

        return new LeadCard(leader.getId(), leader.getCardName(), leader.getDamage(),
                leader.getHealth(), leader.getCardType());
    }

    private World mapToWorld(WorldEntity entity) {
        List<WorldCard> worldCards = entity.getWorldCards().stream()
                .map(mapper::fromEntity)
                .collect(Collectors.toList());

        List<LeadCard> leaderCards = entity.getLeaderCards().stream()
                .map(l -> new LeadCard(l.getId(), l.getCardName(), l.getDamage(), l.getHealth(), l.getCardType()))
                .collect(Collectors.toList());

        return new World(entity.getId(), 0L, entity.getName(), null, worldCards, leaderCards);
    }

    private LeaderCardEntity toLeaderEntity(LeadCard card) {
        LeaderCardEntity entity = new LeaderCardEntity();
        entity.setId(card.getId());
        entity.setCardName(card.getCardName());
        entity.setDamage(card.getDamage());
        entity.setHealth(card.getHealth());
        entity.setCardType(card.getCardType());
        return entity;
    }
}