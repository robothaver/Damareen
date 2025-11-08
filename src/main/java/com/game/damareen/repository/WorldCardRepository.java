package com.game.damareen.repository;

import com.game.damareen.db.entity.CardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorldCardRepository extends JpaRepository<CardEntity, Long> {
}
