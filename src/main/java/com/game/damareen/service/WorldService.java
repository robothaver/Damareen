package com.game.damareen.service;

import com.game.damareen.domain.World;
import com.game.damareen.domain.card.CardDerivation;
import com.game.damareen.domain.card.CardType;
import com.game.damareen.domain.card.LeadCard;
import com.game.damareen.domain.card.WorldCard;

import java.util.List;

public interface WorldService {
    World createWorld(String name, List<WorldCard> cards, List<LeadCard> leaders);
    World getWorldById(long worldId);
    WorldCard addWorldCard(long worldId, String name, int damage, int health, CardType type);
    LeadCard createLeaderCard(long worldId, String baseCardName, String leaderName, CardDerivation derivation);
}