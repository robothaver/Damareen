package com.game.damareen.repository;

import com.game.damareen.db.entity.DungeonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DungeonRepository extends JpaRepository<DungeonEntity, Long> {
}
