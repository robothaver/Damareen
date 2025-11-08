package com.game.damareen.domain;

import com.game.damareen.domain.card.WorldCard;
import lombok.Data;

@Data
public class BattleRound {
    private WorldCard playerCard;
    private WorldCard dungeonCard;
    private String winner;
    private String reason;

    public BattleRound(WorldCard playerCard, WorldCard dungeonCard, String winner, String reason) {
        this.playerCard = playerCard;
        this.dungeonCard = dungeonCard;
        this.winner = winner;
        this.reason = reason;
    }
}