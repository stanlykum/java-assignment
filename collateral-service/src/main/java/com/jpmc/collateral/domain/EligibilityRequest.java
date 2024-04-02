package com.jpmc.collateral.domain;

import lombok.*;

import java.util.List;

/**
 * Represents  the eligibility request for a list of assetsIds and accountsIds.
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EligibilityRequest {
    private List<String> accountIDs;
    private List<String> assetIDs;
}