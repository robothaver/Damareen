package com.game.damareen.controller;

import com.game.damareen.domain.dungeon.Dungeon;
import com.game.damareen.domain.request.DungeonCreateRequest;
import com.game.damareen.service.DungeonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dungeon")
@RequiredArgsConstructor
public class DungeonController {
    private final DungeonService dungeonService;

    @PostMapping("/create")
    public ResponseEntity<Dungeon> getDungeon(@Valid DungeonCreateRequest request) {
        System.out.println(request);
        return ResponseEntity.ok(dungeonService.createDungeon(request));
    }
}