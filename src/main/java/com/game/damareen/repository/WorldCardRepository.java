package com.game.damareen.repository;

import com.game.damareen.db.entity.WorldCardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorldCardRepository extends JpaRepository<WorldCardEntity, Long> {
    List<WorldCardEntity> findAllByPlayer_Id(Long playerId);
}
