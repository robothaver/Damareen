package com.game.damareen.db.entity;

import com.game.damareen.domain.card.CardDerivation;
import com.game.damareen.domain.card.CardType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "lead_cards")
@Inheritance(strategy = InheritanceType.JOINED)
public class LeadCardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id", nullable = false)
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

