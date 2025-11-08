package com.game.damareen.domain.dungeon;

public enum DungeonType {
    SIMPLE_ENCOUNTER(1, 0, RewardType.BONUS_DAMAGE, 1),
    SMALL_DUNGEON(3, 1, RewardType.BONUS_HEALTH, 2),
    LARGE_DUNGEON(5, 1, RewardType.BONUS_DAMAGE, 3);

    private final int regularCardCount;
    private final int leaderCardCount;
    private final RewardType rewardType;
    private final int rewardAmount;

    DungeonType(int regularCardCount, int leaderCardCount, RewardType rewardType, int rewardAmount) {
        this.regularCardCount = regularCardCount;
        this.leaderCardCount = leaderCardCount;
        this.rewardType = rewardType;
        this.rewardAmount = rewardAmount;
    }

    public int getRegularCardCount() {
        return regularCardCount;
    }

    public int getLeaderCardCount() {
        return leaderCardCount;
    }

    public RewardType getRewardType() {
        return rewardType;
    }

    public int getRewardAmount() {
        return rewardAmount;
    }

    public int getTotalCardCount() {
        return regularCardCount + leaderCardCount;
    }

    public boolean requiresLeader() {
        return leaderCardCount > 0;
    }
}