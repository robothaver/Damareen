package com.game.damareen.domain;

import lombok.Data;

import java.util.List;

@Data
public class BattleResult {
    private List<BattleRound> rounds;
    private boolean playerWon;
    private int playerWins;
    private int dungeonWins;
    private String rewardMessage;
}