package com.jpmc.position.controller;

import com.jpmc.position.domain.Position;
import com.jpmc.position.repository.PositionRepository;
import com.jpmc.position.usecase.PositionGeneratorUsecase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Responsible for to retrieve positions for a given list of account IDs,
 * and another to save a list of positions
 */

@RestController
@RequestMapping("/api/v1/positions")
@RequiredArgsConstructor
public class PositionController {
    private final PositionGeneratorUsecase positionGeneratorUsecase;

    private final PositionRepository positionRepository;

    /**
     * This method is used to get list of positions for the give accountIDs and
     * assetIDs
     *
     * @param accountIDs
     * @return This list of position objects
     */
    @PostMapping
    @Operation(summary = "Get positions by list of accountIDs", description = "Get positions for the given accountIDs.")
    @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = List.class)))
    List<Position> getPositions(@RequestBody List<String> accountIDs) {
        var positions = positionGeneratorUsecase.getPositions(accountIDs);
        return positions;
    }

    /**
     * This method is used to persist list of positions
     *
     * @param positions
     * @return Http status code with 201
     */
    @PostMapping("/save")
    @Operation(summary = "Save position", description = "Save position for the given list of positions.")
    @ApiResponse(responseCode = "201", description = "Success", content = @Content(schema = @Schema(implementation = List.class)))
    public ResponseEntity<Void> save(@RequestBody List<Position> positions) {
        positionRepository.saveAllAndFlush(positions);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
