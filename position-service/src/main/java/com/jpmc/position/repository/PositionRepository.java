package com.jpmc.position.repository;

import com.jpmc.position.domain.Position;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * It includes a custom query method findByAccountIdIn that allows you
 * to retrieve Position entities based on a list of account IDs.
 */
public interface PositionRepository extends JpaRepository<Position, Long> {
    List<Position> findByAccountIdIn(List<String> accountIds);
}