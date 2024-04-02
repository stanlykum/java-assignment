package com.jpmc.collateral.domain;

import lombok.*;

/**
 * Represents a position for an account.
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Price {
    private String assetId;
    private double price;
}
