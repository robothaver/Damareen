package com.game.damareen.domain.request;

import com.game.damareen.domain.card.CardDerivation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateLeadCardRequest {
    @NotNull
    private final Long worldCardId;
    @NotBlank
    @Size(max = 16, min = 5)
    private final String name;
    @NotNull
    private final CardDerivation derivation;
}
