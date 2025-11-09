package com.game.damareen.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.game.damareen.domain.card.CardType;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "world_cards")
@Inheritance(strategy = InheritanceType.JOINED)
public class WorldCardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id", nullable = false)
    @JsonIgnore
    private PlayerEntity player;

    @Column(nullable = false)
    private String cardName;

    @Column(nullable = false)
    private int damage;

    @Column(nullable = false)
    private int health;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CardType cardType;
}