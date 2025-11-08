package com.game.damareen.domain.request;

import com.game.damareen.domain.dungeon.DungeonType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class DungeonCreateRequest {
    @NotBlank(message = "Dungeon name can't be blank")
    @Max(value = 16, message = "Name can't be longer than 16 characters")
    private final String name;
    @NotNull
    private final DungeonType type;
    @NotNull
    @NotEmpty(message = "Cards list cannot be empty.")
    private final List<Long> cards;
    @NotNull
    private final Long leadCard;
}
