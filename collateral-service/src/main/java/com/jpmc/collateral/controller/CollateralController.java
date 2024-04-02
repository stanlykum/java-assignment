package com.jpmc.collateral.controller;

import com.jpmc.collateral.domain.CollateralValue;
import com.jpmc.collateral.usecase.CollateralServiceUsecase;
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
 * Responsible for calculating the collateral value for a given list of account IDs
 * and returning the result as a list of CollateralValue objects.
 */
@RestController
@RequestMapping("/api/v1/collaterals")
@RequiredArgsConstructor
public class CollateralController {

    private final CollateralServiceUsecase collateralServiceUsecase;

    /**
     * Responsible for calculating the collateral value for a given list of account IDs
     * and returning the result as a list of CollateralValue objects.
     *
     * @param accountdIds
     * @return The list of Collateral objects
     */
    @PostMapping("/calculate")
    @Operation(summary = "Calculate collateral value by accountdIds", description = "Calculate collateral value for the given accountdIds.")
    @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = List.class)))
    public ResponseEntity<List<CollateralValue>> calculateCollateralValue(@RequestBody List<String> accountdIds) {
        var collateralValues = collateralServiceUsecase.calculateCollateralValue(accountdIds);
        return new ResponseEntity<>(collateralValues, HttpStatus.OK);
    }

}
