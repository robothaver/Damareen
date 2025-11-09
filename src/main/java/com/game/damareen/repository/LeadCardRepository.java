package com.game.damareen.repository;

import com.game.damareen.db.entity.LeadCardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeadCardRepository extends JpaRepository<LeadCardEntity, Long> {
    List<LeadCardEntity> findAllByPlayer_Id(Long playerId);
}
