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
public class Position {
    private String accountId;
    private String assetId;
    private int quantity;
}
