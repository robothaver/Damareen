package com.game.damareen.db.entity;

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
    private List<CardEntity> cards = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "world_id")
    private WorldEntity world;

    public DungeonEntity(String name, DungeonType type) {
        this.name = name;
        this.type = type;
        this.cards = new ArrayList<>();
    }
}
