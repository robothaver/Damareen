package com.game.damareen.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class BattleResult {
    private List<BattleRound> rounds;
    private boolean playerWon;
    private int playerWins;
    private int dungeonWins;
    private String rewardMessage;

    public BattleResult() {
    }

    public BattleResult(List<BattleRound> rounds, boolean playerWon, int playerWins, int dungeonWins, String rewardMessage) {
        this.rounds = rounds;
        this.playerWon = playerWon;
        this.playerWins = playerWins;
        this.dungeonWins = dungeonWins;
        this.rewardMessage = rewardMessage;
    }

    @Override
    public String toString() {
        return "BattleResult{" +
                "rounds=" + rounds +
                ", playerWon=" + playerWon +
                ", playerWins=" + playerWins +
                ", dungeonWins=" + dungeonWins +
                ", rewardMessage='" + rewardMessage + '\'' +
                '}';
    }
}