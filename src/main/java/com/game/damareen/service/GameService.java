package com.game.damareen.service;

import com.game.damareen.domain.BattleResult;
import com.game.damareen.domain.BattleRound;
import com.game.damareen.db.entity.CardEntity;
import com.game.damareen.db.entity.DungeonEntity;
import com.game.damareen.db.entity.GameEntity;
import com.game.damareen.db.entity.WorldEntity;
import com.game.damareen.domain.dungeon.DungeonType;
import com.game.damareen.domain.dungeon.RewardType;
import com.game.damareen.exception.*;
import com.game.damareen.repository.DungeonRepository;
import com.game.damareen.repository.GameRepository;
import com.game.damareen.repository.WorldRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GameService {
    private final GameRepository gameRepository;
    private final WorldRepository worldRepository;
    private final DungeonRepository dungeonRepository;
    private final BattleService battleService;

    @Transactional
    public GameEntity createGame(String playerName, Long worldId, List<String> initialCollection) {
        WorldEntity world = worldRepository.findById(worldId)
                .orElseThrow(() -> new WorldNotFoundException("World not found: " + worldId));

        GameEntity game = new GameEntity(playerName, world);

        List<CardEntity> collectionCards = new ArrayList<>();
        for (String cardName : initialCollection) {
            CardEntity card = world.getWorldCards().stream()
                    .filter(c -> c.getCardName().equals(cardName))
                    .findFirst()
                    .orElseThrow(() -> new CardNotFoundException("Card not found in world: " + cardName));

            CardEntity collectionCard = new CardEntity(
                    null, card.getCardName(), card.getDamage(),
                    card.getHealth(), card.getCardType()
            );
            collectionCards.add(collectionCard);
        }
        game.setCollection(collectionCards);

        return gameRepository.save(game);
    }

    @Transactional
    public void setDeck(Long gameId, List<String> cardNames) {
        GameEntity game = gameRepository.findById(gameId)
                .orElseThrow(() -> new GameNotFoundException("Game not found: " + gameId));

        for (String cardName : cardNames) {
            boolean exists = game.getCollection().stream()
                    .anyMatch(c -> c.getCardName().equals(cardName));
            if (!exists) {
                throw new CardNotFoundException("Card not in collection: " + cardName);
            }
        }

        game.setDeck(cardNames);
        gameRepository.save(game);
    }

    @Transactional
    public BattleResult startBattle(Long gameId, Long dungeonId) {
        GameEntity game = gameRepository.findById(gameId)
                .orElseThrow(() -> new GameNotFoundException("Game not found: " + gameId));

        DungeonEntity dungeon = dungeonRepository.findById(dungeonId)
                .orElseThrow(() -> new DungeonNotFoundException("Dungeon not found: " + dungeonId));

        if (game.getDeck().size() != dungeon.getCards().size()) {
            throw new InvalidDeckSizeException(
                    String.format("Deck size (%d) does not match dungeon size (%d)",
                            game.getDeck().size(), dungeon.getCards().size()));
        }

        List<BattleRound> rounds = new ArrayList<>();
        int playerWins = 0;
        int dungeonWins = 0;

        for (int i = 0; i < game.getDeck().size(); i++) {
            String deckCardName = game.getDeck().get(i);
            CardEntity playerCard = game.getCollection().stream()
                    .filter(c -> c.getCardName().equals(deckCardName))
                    .findFirst()
                    .orElseThrow(() -> new CardNotFoundException("Deck card not found in collection: " + deckCardName));

            CardEntity dungeonCard = dungeon.getCards().get(i);

            BattleRound round = battleService.evaluateBattle(playerCard, dungeonCard);
            rounds.add(round);

            if ("player".equals(round.getWinner())) {
                playerWins++;
            } else {
                dungeonWins++;
            }
        }

        boolean playerWon = playerWins >= Math.ceil(dungeon.getCards().size() / 2.0);

        BattleResult result = new BattleResult();
        result.setRounds(rounds);
        result.setPlayerWon(playerWon);
        result.setPlayerWins(playerWins);
        result.setDungeonWins(dungeonWins);

        if (playerWon) {
            String rewardMsg = applyReward(game, dungeon.getType());
            result.setRewardMessage(rewardMsg);
            gameRepository.save(game);
        } else {
            result.setRewardMessage("No reward - dungeon won");
        }

        return result;
    }

    private String applyReward(GameEntity game, DungeonType dungeonType) {
        RewardType rewardType = dungeonType.getRewardType();
        int rewardAmount = dungeonType.getRewardAmount();

        if (game.getCollection().isEmpty()) {
            return "No cards to upgrade";
        }

        CardEntity cardToUpgrade = game.getCollection().get(0);

        if (rewardType == RewardType.BONUS_DAMAGE) {
            cardToUpgrade.setDamage(cardToUpgrade.getDamage() + rewardAmount);
            return String.format("Card '%s' gained +%d damage (now %d)",
                    cardToUpgrade.getCardName(), rewardAmount, cardToUpgrade.getDamage());
        } else if (rewardType == RewardType.BONUS_HEALTH) {
            cardToUpgrade.setHealth(cardToUpgrade.getHealth() + rewardAmount);
            return String.format("Card '%s' gained +%d health (now %d)",
                    cardToUpgrade.getCardName(), rewardAmount, cardToUpgrade.getHealth());
        }

        return "Unknown reward type";
    }

    public GameEntity getGame(Long gameId) {
        return gameRepository.findById(gameId)
                .orElseThrow(() -> new GameNotFoundException("Game not found: " + gameId));
    }

    @Transactional
    public void upgradeCard(Long gameId, String cardName, RewardType rewardType, int amount) {
        GameEntity game = gameRepository.findById(gameId)
                .orElseThrow(() -> new GameNotFoundException("Game not found: " + gameId));

        CardEntity card = game.getCollection().stream()
                .filter(c -> c.getCardName().equals(cardName))
                .findFirst()
                .orElseThrow(() -> new CardNotFoundException("Card not in collection: " + cardName));

        if (rewardType == RewardType.BONUS_DAMAGE) {
            card.setDamage(card.getDamage() + amount);
        } else if (rewardType == RewardType.BONUS_HEALTH) {
            card.setHealth(card.getHealth() + amount);
        }

        gameRepository.save(game);
    }
}