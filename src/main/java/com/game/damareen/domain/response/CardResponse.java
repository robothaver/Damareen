package com.game.damareen.domain.response;

import com.game.damareen.domain.card.LeadCard;
import com.game.damareen.domain.card.WorldCard;
import lombok.Data;

import java.util.List;

@Data
public class CardResponse {
    private final List<WorldCard> worldCards;
    private final List<LeadCard> leadCards;
}
