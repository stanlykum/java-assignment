package com.jpmc.price.controller;

import com.jpmc.price.domain.Price;
import com.jpmc.price.repository.PriceRepository;
import com.jpmc.price.usecase.PriceGeneratorUsecase;
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
 * Responsible for to retrieve prices for a given list of asset IDs,
 * and another to save a list of prices
 */

@RestController
@RequestMapping("/api/v1/prices")
@RequiredArgsConstructor
public class PriceController {
    private final PriceGeneratorUsecase priceGeneratorUsecase;
    private final PriceRepository priceRepository;

    /**
     * This method is used to get list of prices for the give assetIDs.
     *
     * @param assetIds
     * @return This list of prices objects
     */

    @PostMapping("/assetIds")
    @Operation(summary = "Get prices by list of assetIDs", description = "Get prices for the given assetIDs.")
    @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = List.class)))
    public ResponseEntity<List<Price>> getPrices(@RequestBody List<String> assetIds) {
        List<Price> prices = priceGeneratorUsecase.getPrices(assetIds);
        return ResponseEntity.ok(prices);
    }

    /**
     * This method is used to persist list of prices
     *
     * @param prices
     * @return Http status code with 201
     */
    @PostMapping("/save")
    @Operation(summary = "Save prices", description = "Save prices for the given list of prices.")
    @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = List.class)))
    public ResponseEntity<Void> savePrices(@RequestBody List<Price> prices) {
        priceRepository.saveAllAndFlush(prices);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
