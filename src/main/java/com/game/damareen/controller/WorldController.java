package com.game.damareen.controller;

import com.game.damareen.domain.World;
import com.game.damareen.domain.card.CardDerivation;
import com.game.damareen.domain.card.CardType;
import com.game.damareen.domain.card.LeadCard;
import com.game.damareen.domain.card.WorldCard;
import com.game.damareen.service.WorldService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/world")
@RequiredArgsConstructor
public class WorldController {
    private final WorldService worldService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<World> createWorld(@Valid @RequestBody CreateWorldRequest request) {
        World world = worldService.createWorld(
                request.getWorldName(),
                new ArrayList<>(),
                new ArrayList<>()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(world);
    }

    @GetMapping("/{worldId}")
    public ResponseEntity<World> getWorld(@PathVariable Long worldId) {
        return ResponseEntity.ok(worldService.getWorldById(worldId));
    }

    @PostMapping("/{worldId}/card")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<WorldCard> addCard(
            @PathVariable Long worldId,
            @Valid @RequestBody AddCardRequest request) {
        WorldCard card = worldService.addWorldCard(
                worldId,
                request.getName(),
                request.getDamage(),
                request.getHealth(),
                request.getCardType()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(card);
    }

    @PostMapping("/{worldId}/leader")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<LeadCard> createLeader(
            @PathVariable Long worldId,
            @Valid @RequestBody CreateLeaderRequest request) {
        LeadCard leader = worldService.createLeaderCard(
                worldId,
                request.getBaseCardName(),
                request.getLeaderName(),
                request.getDerivation()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(leader);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateWorldRequest {
        @NotBlank(message = "World name can't be blank")
        private String worldName;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AddCardRequest {
        @NotBlank(message = "Card name can't be blank")
        @Max(value = 16, message = "Name can't be longer than 16 characters")
        private String name;

        @NotNull(message = "Damage cannot be null")
        @Min(value = 2, message = "Damage must be at least 2")
        @Max(value = 100, message = "Damage cannot exceed 100")
        private Integer damage;

        @NotNull(message = "Health cannot be null")
        @Min(value = 1, message = "Health must be at least 1")
        @Max(value = 100, message = "Health cannot exceed 100")
        private Integer health;

        @NotNull(message = "Card type cannot be null")
        private CardType cardType;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateLeaderRequest {
        @NotBlank(message = "Base card name can't be blank")
        private String baseCardName;

        @NotBlank(message = "Leader name can't be blank")
        @Max(value = 16, message = "Name can't be longer than 16 characters")
        private String leaderName;

        @NotNull(message = "Derivation type cannot be null")
        private CardDerivation derivation;
    }
}