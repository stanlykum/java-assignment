package com.jpmc.position.usecase;

import com.jpmc.position.domain.Position;

import java.util.List;

public interface PositionGenerator {
    /**
     * Retrieves the posistions for the given list of accountIDs
     *
     * @param accountIDs The list of account IDs
     * @return The list of positions for the accountIDs
     */
    List<Position> getPositions(List<String> accountIDs);
}
