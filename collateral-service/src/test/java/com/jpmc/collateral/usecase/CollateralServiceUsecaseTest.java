package com.jpmc.collateral.usecase;

import com.github.benmanes.caffeine.cache.Cache;
import com.jpmc.collateral.client.EligibilityClient;
import com.jpmc.collateral.client.PositionClient;
import com.jpmc.collateral.client.PriceClient;
import com.jpmc.collateral.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class CollateralServiceUsecaseTest {
    @Mock
    private PositionClient positionClient;

    @Mock
    private EligibilityClient eligibilityClient;

    @Mock
    private PriceClient priceClient;

    @Mock
    private Cache<List<String>, List<Position>> positionCache;

    @Mock
    private Cache<EligibilityRequest, List<Eligibility>> eligibilityCache;

    @Mock
    private Cache<List<String>, List<Price>> priceCache;

    @InjectMocks
    private CollateralServiceUsecase collateralService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Set up the cache mocks with the expected values
        var positions = Arrays.asList(
                Position.builder().accountId("account1").assetId("asset1").quantity(100).build(),
                Position.builder().accountId("account1").assetId("asset2").quantity(50).build(),
                Position.builder().accountId("account2").assetId("asset2").quantity(75).build(),
                Position.builder().accountId("account3").assetId("asset3").quantity(25).build()
        );

        var eligibilities = Arrays.asList(
                Eligibility.builder().eligible(true).discount(0.9).assetIDs(Arrays.asList("asset1", "asset2")).build(),
                Eligibility.builder().eligible(false).discount(0.0).assetIDs(Arrays.asList("asset3")).build()
        );

        var prices = Arrays.asList(
                Price.builder().assetId("asset1").price(100.0).build(),
                Price.builder().assetId("asset2").price(50.0).build(),
                Price.builder().assetId("asset3").price(40.0).build()
        );

        when(positionCache.get(any(), any())).thenReturn(positions);
        when(eligibilityCache.get(any(), any())).thenReturn(eligibilities);
        when(priceCache.get(any(), any())).thenReturn(prices);

        collateralService = new CollateralServiceUsecase(positionClient, eligibilityClient, priceClient, positionCache, eligibilityCache, priceCache);
    }

    @Test
    @DisplayName("Should calculate collateral value")
    void calculateCollateralValue() {
        var accounts = Arrays.asList("account1", "account2", "account3");

        var result = collateralService.calculateCollateralValue(accounts);

        assertThat(result).hasSize(3)
                .extracting(CollateralValue::getAccountId, CollateralValue::getCollateralValue)
                .containsExactlyInAnyOrder(
                        tuple("account1", new BigDecimal("11250.00")),
                        tuple("account2", new BigDecimal("3375.00")),
                        tuple("account3", new BigDecimal("0.00"))
                );
    }
}
