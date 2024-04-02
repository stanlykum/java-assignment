package com.jpmc.position.usecase;

import com.jpmc.position.domain.Position;
import com.jpmc.position.repository.PositionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of the PositionService interface
 */
@Service
@RequiredArgsConstructor
public class PositionGeneratorUsecase implements PositionGenerator {

    private final PositionRepository positionRepository;

    /**
     * Retrieves the positions of the given list of accountIDs.
     *
     * @param accountIDs The list of account IDs.
     * @return The list of the positions for the accountIDs.
     */

    @Override
    public List<Position> getPositions(List<String> accountIDs) {
        return positionRepository.findByAccountIdIn(accountIDs);
    }
}
