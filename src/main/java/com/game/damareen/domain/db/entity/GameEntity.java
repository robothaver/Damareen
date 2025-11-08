package com.game.damareen.domain.db.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "games")
public class GameEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String playerName;

    @ManyToOne
    @JoinColumn(name = "world_id", nullable = false)
    private WorldEntity world;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "game_id")
    private List<CardEntity> collection = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "game_deck", joinColumns = @JoinColumn(name = "game_id"))
    @Column(name = "card_name")
    @OrderColumn(name = "deck_order")
    private List<String> deck = new ArrayList<>();

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public GameEntity(String playerName, WorldEntity world) {
        this.playerName = playerName;
        this.world = world;
        this.collection = new ArrayList<>();
        this.deck = new ArrayList<>();
    }
}
