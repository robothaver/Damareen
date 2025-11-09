package com.game.damareen.controller;

import com.game.damareen.domain.card.LeadCard;
import com.game.damareen.domain.card.WorldCard;
import com.game.damareen.domain.request.CreateLeadCardRequest;
import com.game.damareen.domain.request.WorldCardCreateRequest;
import com.game.damareen.domain.response.CardResponse;
import com.game.damareen.service.CardService;
import com.game.damareen.service.PlayerHelperService;
import com.game.damareen.service.PlayerService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/card")
@RequiredArgsConstructor
public class CardController {
    private final CardService cardService;
    private final PlayerHelperService playerHelperService;
    private final PlayerService playerService;

    @PostMapping("/{player}/create")
    public ResponseEntity<WorldCard> createWorldCard(@Valid @RequestBody WorldCardCreateRequest request, @Valid @NotBlank @PathVariable String player) {
        long playerId = playerHelperService.createPlayerIfNotExists(player);
        WorldCard worldCard = cardService.createWorldCard(request, playerId);
        return ResponseEntity.ok(worldCard);
    }

    @GetMapping("/{player}")
    public ResponseEntity<CardResponse> getWorldCards(@Valid @NotBlank @PathVariable String player) {
        Long playerId = playerService.getPlayerId(player);
        if (playerId == null) throw new IllegalArgumentException("Player not found in card request");
        List<WorldCard> worldCards = cardService.getWorldCards(playerId);
        List<LeadCard> leadCards = cardService.getLeadCards(playerId);

        return ResponseEntity.ok(new CardResponse(worldCards, leadCards));
    }

    @PostMapping("/{player}/lead")
    public ResponseEntity<LeadCard> createLeadCard(@Valid @RequestBody CreateLeadCardRequest request, @Valid @NotBlank @PathVariable String player) {
        long playerId = playerHelperService.createPlayerIfNotExists(player);
        LeadCard worldCard = cardService.enhaceCard(request, playerId);
        return ResponseEntity.ok(worldCard);
    }
}
