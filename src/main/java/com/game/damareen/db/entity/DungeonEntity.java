package com.game.damareen.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.game.damareen.domain.dungeon.DungeonType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "dungeons")
public class DungeonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id", nullable = false)
    @JsonIgnore
    private PlayerEntity player;

    @Column(nullable = false, unique = true)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DungeonType type;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "dungeon_cards",
            joinColumns = @JoinColumn(name = "dungeon_id"),
            inverseJoinColumns = @JoinColumn(name = "card_id")
    )
    @OrderColumn(name = "card_order")
    private List<WorldCardEntity> cards = new ArrayList<>();
}