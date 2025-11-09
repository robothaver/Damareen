package com.game.damareen.controller;

import com.game.damareen.domain.dungeon.Dungeon;
import com.game.damareen.domain.request.DungeonCreateRequest;
import com.game.damareen.service.DungeonService;
import com.game.damareen.service.PlayerHelperService;
import com.game.damareen.service.PlayerService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dungeon")
@RequiredArgsConstructor
public class DungeonController {
    private final DungeonService dungeonService;
    private final PlayerService playerService;
    private final PlayerHelperService playerHelperService;

    @PostMapping("/{player}/create")
    public ResponseEntity<Dungeon> createDungeon(@Valid @RequestBody DungeonCreateRequest request,  @PathVariable String player) {
        Long playerId = playerHelperService.createPlayerIfNotExists(player);
        return ResponseEntity.ok(dungeonService.createDungeon(request, playerId));
    }

    @GetMapping("/{player}/{id}")
    public ResponseEntity<Dungeon> getDungeon(@PathVariable Long id, @Valid @NotBlank @PathVariable String player) {
        Long playerId = playerService.getPlayerId(player);
        if (playerId == null) throw new IllegalArgumentException("Player not found in card request");

        return ResponseEntity.ok(dungeonService.getDungeonById(id, playerId));
    }

    @GetMapping("/{player}")
    public ResponseEntity<List<Dungeon>> getDungeons(@Valid @NotBlank @PathVariable String player) {
        Long playerId = playerService.getPlayerId(player);
        if (playerId == null) throw new IllegalArgumentException("Player not found in card request");

        return ResponseEntity.ok(dungeonService.getDungeons(playerId));
    }
}