package com.jpmc.collateral.usecase;

import com.github.benmanes.caffeine.cache.Cache;
import com.jpmc.collateral.client.EligibilityClient;
import com.jpmc.collateral.client.PositionClient;
import com.jpmc.collateral.client.PriceClient;
import com.jpmc.collateral.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Implementation of the CollateralService interface
 */
@Service
@RequiredArgsConstructor
public class CollateralServiceUsecase implements CollateralService {
    private final PositionClient positionClient;
    private final EligibilityClient eligibilityClient;
    private final PriceClient priceClient;

    private final Cache<List<String>, List<Position>> positionCache;
    private final Cache<EligibilityRequest, List<Eligibility>> eligibilityCache;
    private final Cache<List<String>, List<Price>> priceCache;

    /**
     * Responsible for calculating the collateral value for a given list of account IDs.
     * It uses the PositionClient, EligibilityClient, and PriceClient to fetch the necessary data,
     * and then performs the collateral value calculation for each account.
     *
     * @param accounts
     * @return The list of Collateral objects
     */

    @Override
    @Cacheable(cacheNames = {"positions", "eligibilities", "prices"}, condition = "#result != null && !#result.isEmpty()")
    public List<CollateralValue> calculateCollateralValue(List<String> accounts) {
        // 1. Get the positions for the given accounts from the cache
        var positions = positionCache.get(accounts, this::getPositions);

        // 2. Get eligibility and discount factor for the positions from the cache
        var eligibilities = eligibilityCache.get(EligibilityRequest.builder().accountIDs(accounts).assetIDs(getAssetIds(positions)).build(), this::getEligibilities);

        // Create maps for efficient lookup of eligibility and price information
        var eligibilityMap = eligibilities.stream()
                .flatMap(e -> e.getAssetIDs().stream().map(assetId -> Map.entry(assetId, e)))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        // 3. Get prices for the assets from the cache
        var prices = priceCache.get(getAssetIds(positions), this::getPrices);

        var priceMap = prices.stream()
                .collect(Collectors.toMap(Price::getAssetId, Function.identity()));

        // 4. Calculate collateral value for each account
        return accounts.stream()
                .map(account -> new CollateralValue(account, calculateCollateralValueForAccount(account, positions, eligibilityMap, priceMap)))
                .collect(Collectors.toList());
    }

    public List<Position> getPositions(List<String> accounts) {
        List<Position> positions = positionClient.getPositions(accounts);
        return positions != null ? positions : Collections.emptyList();
    }

    public List<Eligibility> getEligibilities(EligibilityRequest request) {
        List<Eligibility> eligibilities = eligibilityClient.getEligibilities(request);
        return eligibilities != null ? eligibilities : Collections.emptyList();
    }

    public List<Price> getPrices(List<String> assetIds) {
        List<Price> prices = priceClient.getPrices(assetIds);
        return prices != null ? prices : Collections.emptyList();
    }

    private BigDecimal calculateCollateralValueForAccount(String accountId, List<Position> positions, Map<String, Eligibility> eligibilityMap, Map<String, Price> priceMap) {
        return positions.stream()
                .filter(p -> p.getAccountId().equals(accountId))
                .map(p -> {
                    Eligibility eligibility = eligibilityMap.get(p.getAssetId());
                    Price price = priceMap.get(p.getAssetId());
                    if (eligibility != null && eligibility.getEligible()) {
                        return BigDecimal.valueOf(p.getQuantity())
                                .multiply(BigDecimal.valueOf(price.getPrice()))
                                .multiply(BigDecimal.valueOf(eligibility.getDiscount()));
                    } else {
                        return BigDecimal.ZERO;
                    }
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);
    }

    private List<String> getAssetIds(List<Position> positions) {
        return positions.stream()
                .map(Position::getAssetId).distinct().collect(Collectors.toList());
    }
}