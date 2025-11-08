package com.game.damareen.controller;

import com.game.damareen.domain.BattleResult;
import com.game.damareen.db.entity.GameEntity;
import com.game.damareen.domain.dungeon.RewardType;
import com.game.damareen.service.GameService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/game")
@RequiredArgsConstructor
public class GameController {
    private final GameService gameService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<GameEntity> createGame(@Valid @RequestBody CreateGameRequest request) {
        GameEntity game = gameService.createGame(
                request.getPlayerName(),
                request.getWorldId(),
                request.getInitialCollection()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(game);
    }

    @GetMapping("/{gameId}")
    public ResponseEntity<GameEntity> getGame(@PathVariable Long gameId) {
        return ResponseEntity.ok(gameService.getGame(gameId));
    }

    @PostMapping("/{gameId}/deck")
    public ResponseEntity<Void> setDeck(
            @PathVariable Long gameId,
            @Valid @RequestBody SetDeckRequest request) {
        gameService.setDeck(gameId, request.getCardNames());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{gameId}/battle/{dungeonId}")
    public ResponseEntity<BattleResult> startBattle(
            @PathVariable Long gameId,
            @PathVariable Long dungeonId) {
        BattleResult result = gameService.startBattle(gameId, dungeonId);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/{gameId}/upgrade")
    public ResponseEntity<Void> upgradeCard(
            @PathVariable Long gameId,
            @Valid @RequestBody UpgradeCardRequest request) {
        gameService.upgradeCard(gameId, request.getCardName(), request.getRewardType(), request.getAmount());
        return ResponseEntity.ok().build();
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateGameRequest {
        @NotNull(message = "Player name cannot be null")
        private String playerName;

        @NotNull(message = "World ID cannot be null")
        private Long worldId;

        @NotEmpty(message = "Initial collection cannot be empty")
        private List<String> initialCollection;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SetDeckRequest {
        @NotEmpty(message = "Card names cannot be empty")
        private List<String> cardNames;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpgradeCardRequest {
        @NotNull(message = "Card name cannot be null")
        private String cardName;

        @NotNull(message = "Reward type cannot be null")
        private RewardType rewardType;

        @NotNull(message = "Amount cannot be null")
        private Integer amount;
    }
}