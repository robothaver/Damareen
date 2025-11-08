package com.game.damareen.domain.db;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "world_card")
@Inheritance(strategy = InheritanceType.JOINED)
public class WorldCardEntity {
    @Id
    @GeneratedValue(st)
}
