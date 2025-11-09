package com.game.damareen.service;

import com.game.damareen.domain.card.LeadCard;
import com.game.damareen.domain.card.WorldCard;
import com.game.damareen.domain.request.CreateLeadCardRequest;
import com.game.damareen.domain.request.WorldCardCreateRequest;

import java.util.List;

public interface CardService {
    WorldCard createWorldCard(WorldCardCreateRequest request, long playerId);
    LeadCard enhaceCard(CreateLeadCardRequest request, long playerId);

    List<WorldCard> getWorldCards(long playerId);
    List<LeadCard> getLeadCards(long playerId);
}
