package com.jpmc.collateral.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.List;

/**
 * Represents  the eligibility information for a set of assets and accounts.
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Eligibility {
    @JsonIgnore
    private Long id;
    private Boolean eligible;
    private List<String> assetIDs;
    private List<String> accountIDs;
    private double discount;
}
