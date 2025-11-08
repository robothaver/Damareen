package com.game.damareen.domain;

import com.game.damareen.domain.card.WorldCard;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.smartcardio.Card;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BattleRound {
    private WorldCard playerCard;
    private WorldCard dungeonCard;
    private String winner;
    private String reason;
}
