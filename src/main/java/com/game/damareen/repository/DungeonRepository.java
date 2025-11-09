package com.game.damareen.repository;

import com.game.damareen.db.entity.DungeonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DungeonRepository extends JpaRepository<DungeonEntity, Long> {
    List<DungeonEntity> findAllByPlayer_Id(Long playerId);

    boolean existsByName(String name);
}
