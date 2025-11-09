package com.game.damareen.domain.request;

import com.game.damareen.domain.dungeon.DungeonType;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class DungeonCreateRequest {
    @NotBlank(message = "Dungeon name can't be blank")
    private final String name;
    @NotNull
    private final DungeonType type;
    @NotNull
    @NotEmpty(message = "Cards list cannot be empty.")
    private final List<Long> cards;
    private final Long leadCard;
}
