package com.game.damareen.controller;

import com.game.damareen.domain.Player;
import com.game.damareen.service.PlayerService;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/player")
@RequiredArgsConstructor
@Validated
public class PlayerController {
    private final PlayerService playerService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Long> createPlayer(@NotBlank(message = "Player name can't be blank") @RequestParam String playerName) {
        Long playerId = playerService.createPlayer(playerName);
        return ResponseEntity.status(HttpStatus.CREATED).body(playerId);
    }

    @GetMapping("/{playerName}")
    public ResponseEntity<Player> getPlayer(@NotBlank @PathVariable String playerName) {
        Long playerId = playerService.getPlayerId(playerName);
        if (playerId == null) {
            return ResponseEntity.notFound().build();
        }
        Player player = playerService.getPlayerById(playerId);
        return ResponseEntity.ok(player);
    }
}