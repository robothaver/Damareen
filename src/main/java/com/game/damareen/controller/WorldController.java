package com.game.damareen.controller;

import com.game.damareen.domain.request.CreateWorldRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
public class WorldController {

    @PostMapping("/{player}/world/create")
    public void createWorld(@PathVariable String player, @Valid CreateWorldRequest worldRequest) {
        System.out.println("Create world for " + player + " " + worldRequest);
    }
}
