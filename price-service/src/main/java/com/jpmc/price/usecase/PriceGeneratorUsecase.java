package com.jpmc.price.usecase;

import com.jpmc.price.domain.Price;
import com.jpmc.price.repository.PriceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of the PriceService interface.
 */
@Service
@RequiredArgsConstructor
public class PriceGeneratorUsecase implements PriceGenerator {

    private final PriceRepository priceRepository;

    /**
     * Retrives the prices for the given  list of asset IDs.
     *
     * @param assetIds The list of assetIds.
     * @return The list of prices for the assetIds.
     */
    @Override
    public List<Price> getPrices(List<String> assetIds) {
        List<Price> priceEntities = priceRepository.findByAssetIdIn(assetIds);
        return priceEntities.stream()
                .map(entity -> Price.builder()
                        .assetId(entity.getAssetId())
                        .price(entity.getPrice())
                        .build())
                .collect(Collectors.toList());
    }
}
