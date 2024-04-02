package com.jpmc.collateral.usecase;

import com.jpmc.collateral.domain.CollateralValue;

import java.util.List;

/**
 * Service interface for calculating the collateral value
 */

public interface CollateralService {
    /**
     * Calculate the collateral value for the given list of accountIds
     *
     * @param accountIds The list of account IDs
     * @return The list of collateral values for the accountIds
     */
    List<CollateralValue> calculateCollateralValue(List<String> accountIds);
}
