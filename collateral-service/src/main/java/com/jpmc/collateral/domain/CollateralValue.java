package com.jpmc.collateral.domain;

import lombok.*;

import java.math.BigDecimal;

/**
 * Represents the collateral value for an account
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CollateralValue {
    private String accountId;
    private BigDecimal collateralValue;
}
