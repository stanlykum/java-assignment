package com.jpmc.price.usecase;

import com.jpmc.price.domain.Price;

import java.util.List;

/**
 * Service interface for fetching asset prices.
 */

public interface PriceGenerator {
    /**
     * Retrieves the prices for the given list of asset IDs.
     *
     * @param assetIDs The list of asset IDs.
     * @return The list of prices  for the assetIDs.
     */
    List<Price> getPrices(List<String> assetIDs);
}
