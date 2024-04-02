package com.jpmc.eligibility.controller;

import com.jpmc.eligibility.domain.Eligibility;
import com.jpmc.eligibility.domain.EligibilityRequest;
import com.jpmc.eligibility.repository.EligibilityRepository;
import com.jpmc.eligibility.usecase.EligibilityGeneratorUsecase;
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

import java.util.Collections;
import java.util.List;

/**
 * class is responsible for handling HTTP requests related to eligibilities.
 * It provides two methods: one to retrieve eligibilities for
 * a given list of account and asset IDs, and another to save a list of eligibilities.
 */

@RestController
@RequestMapping("/api/v1/eligibilities")
@RequiredArgsConstructor
public class EligibilityController {
    private final EligibilityGeneratorUsecase eligibilityGeneratorUsecase;
    private final EligibilityRepository eligibilityRepository;

    /**
     * This method is used to get list of eligiblities for the give accountIDs and
     * assetIDs
     *
     * @param eligibilityRequest
     * @return This list of eligibility objects
     */
    @PostMapping
    @Operation(summary = "Get eligibilities by list of accountIDs and assetIDs", description = "Get eligibilities for the given accountIDs and assetIDs.")
    @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = EligibilityRequest.class)))
    public List<Eligibility> getEligibilities(@RequestBody EligibilityRequest eligibilityRequest) {
        if (eligibilityRequest.getAccountIDs() == null || eligibilityRequest.getAccountIDs().isEmpty() ||
                eligibilityRequest.getAssetIDs() == null || eligibilityRequest.getAssetIDs().isEmpty()) {
            return Collections.emptyList();
        }
        return eligibilityGeneratorUsecase.getEligibilities(eligibilityRequest.getAccountIDs(), eligibilityRequest.getAssetIDs());
    }

    /**
     * This method is used to persist list of eligibility
     *
     * @param eligibilities
     * @return Http status code with 201
     */

    @PostMapping("/save")
    @Operation(summary = "Save eligibilities", description = "Save eligibilities for given for the given list of eligibilities.")
    @ApiResponse(responseCode = "201", description = "Success", content = @Content(schema = @Schema(implementation = List.class)))
    public ResponseEntity<Void> saveEligibilities(@RequestBody List<Eligibility> eligibilities) {
        eligibilityRepository.saveAllAndFlush(eligibilities);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}