package com.game.damareen.controller;

import com.game.damareen.service.PlayerService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/player")
@RequiredArgsConstructor
public class PlayerController {
    private final PlayerService playerService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void createPlayer(@Valid @NotBlank(message = "Player name cant be blank") @RequestParam String playerName) {
        playerService.createPlayer(playerName);
    }
}
