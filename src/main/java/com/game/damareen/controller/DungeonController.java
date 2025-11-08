package com.game.damareen.controller;

import com.game.damareen.domain.dungeon.Dungeon;
import com.game.damareen.domain.request.DungeonCreateRequest;
import com.game.damareen.service.DungeonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dungeon")
@RequiredArgsConstructor
public class DungeonController {
    private final DungeonService dungeonService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Dungeon> createDungeon(@Valid @RequestBody DungeonCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(dungeonService.createDungeon(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Dungeon> getDungeon(@PathVariable Long id) {
        return ResponseEntity.ok(dungeonService.getDungeonById(id));
    }
}