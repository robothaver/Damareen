package com.game.damareen.domain.dungeon;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum DungeonType {
    SMALL(1, RewardType.BONUS_DAMAGE, 1),
    MEDIUM(4, RewardType.BONUS_HEALTH, 2),
    LARGE(6, RewardType.BONUS_DAMAGE, 3);

    private final int cardCount;
    private final RewardType rewardType;
    private final int rewardAmount;
}
