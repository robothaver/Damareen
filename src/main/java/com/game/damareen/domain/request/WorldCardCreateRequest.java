package com.game.damareen.domain.request;

import com.game.damareen.domain.card.CardType;
import jakarta.validation.constraints.*;
import jakarta.websocket.OnMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class WorldCardCreateRequest {
    @NotBlank
    @Size(max = 16)
    private String name;

    @Min(2)
    @Max(100)
    @NotNull
    private Integer damage;

    @Min(1)
    @Max(100)
    @NotNull
    private Integer health;

    @NotNull(message = "Card type cant be null")
    private CardType cardType;
}
