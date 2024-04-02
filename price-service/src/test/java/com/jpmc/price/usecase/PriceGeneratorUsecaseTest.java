package com.jpmc.price.usecase;

import com.jpmc.price.domain.Price;
import com.jpmc.price.repository.PriceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PriceGeneratorUsecaseTest {

    @Mock
    private PriceRepository priceRepository;

    @InjectMocks
    private PriceGeneratorUsecase priceGeneratorUsecase;

    @BeforeEach
    void setUp() {
        // Set up the mock PriceRepository
        var priceEntities = Arrays.asList(
                Price.builder().assetId("S1").price(50.5).build(),
                Price.builder().assetId("S2").price(20.2).build(),
                Price.builder().assetId("S3").price(10.4).build(),
                Price.builder().assetId("S4").price(15.5).build()
        );
        when(priceRepository.findByAssetIdIn(Arrays.asList("asset1", "asset2", "asset3")))
                .thenReturn(priceEntities);
    }

    @Test
    void testGetPrices() {
        // when
        var prices = priceGeneratorUsecase.getPrices(Arrays.asList("asset1", "asset2", "asset3"));

        // then
        assertThat(prices)
                .hasSize(4)
                .containsExactly(
                        Price.builder().assetId("S1").price(50.5).build(),
                        Price.builder().assetId("S2").price(20.2).build(),
                        Price.builder().assetId("S3").price(10.4).build(),
                        Price.builder().assetId("S4").price(15.5).build()
                );
    }
}