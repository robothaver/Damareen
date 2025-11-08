package com.game.damareen.service;

import com.game.damareen.domain.BattleRound;
import com.game.damareen.domain.card.CardType;
import com.game.damareen.domain.card.WorldCard;
import com.game.damareen.domain.db.entity.CardEntity;
import org.springframework.stereotype.Service;

@Service
public class BattleService {
    public BattleRound evaluateBattle(CardEntity playerCard, CardEntity dungeonCard) {
        boolean playerWinsByDamage = playerCard.getDamage() > dungeonCard.getHealth();
        boolean dungeonWinsByDamage = dungeonCard.getDamage() > playerCard.getHealth();

        WorldCard playerDto = toDto(playerCard);
        WorldCard dungeonDto = toDto(dungeonCard);

        if (playerWinsByDamage && !dungeonWinsByDamage) {
            return new BattleRound(
                    playerDto, dungeonDto, "player",
                    String.format("%s damage (%d) > %s health (%d)",
                            playerCard.getCardName(), playerCard.getDamage(),
                            dungeonCard.getCardName(), dungeonCard.getHealth())
            );
        }

        if (dungeonWinsByDamage && !playerWinsByDamage) {
            return new BattleRound(
                    playerDto, dungeonDto, "dungeon",
                    String.format("%s damage (%d) > %s health (%d)",
                            dungeonCard.getCardName(), dungeonCard.getDamage(),
                            playerCard.getCardName(), playerCard.getHealth())
            );
        }

        CardType playerType = playerCard.getCardType();
        CardType dungeonType = dungeonCard.getCardType();

        if (playerType.beats(dungeonType)) {
            return new BattleRound(
                    playerDto, dungeonDto, "player",
                    String.format("%s beats %s by type",
                            playerType, dungeonType)
            );
        }

        if (dungeonType.beats(playerType)) {
            return new BattleRound(
                    playerDto, dungeonDto, "dungeon",
                    String.format("%s beats %s by type",
                            dungeonType, playerType)
            );
        }

        return new BattleRound(
                playerDto, dungeonDto, "dungeon",
                "Draw - dungeon wins by default"
        );
    }

    private WorldCard toDto(CardEntity card) {
        return new WorldCard(
                card.getId(),
                card.getCardName(),
                card.getDamage(),
                card.getHealth(),
                card.getCardType()
        );
    }
}