package com.game.damareen.domain.db.entity;

import com.game.damareen.domain.card.CardDerivation;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "leader_cards")
public class LeaderCardEntity extends CardEntity {
    @Column(nullable = false)
    private String baseCardName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CardDerivation derivationType;
}
