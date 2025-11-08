package com.game.damareen.domain.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateWorldRequest {
    @NotBlank(message = "World name can't be blank")
    private final String worldName;
}
