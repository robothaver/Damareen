package com.game.damareen.repository;

import com.game.damareen.db.entity.WorldEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorldRepository extends JpaRepository<WorldEntity, Long> {
}
