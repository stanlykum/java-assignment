package com.jpmc.collateral.client;

import com.jpmc.collateral.config.FeignConfiguration;
import com.jpmc.collateral.domain.Price;
import com.jpmc.collateral.exception.ErrorCodeEnum;
import com.jpmc.collateral.exception.TechnicalException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Feign client for the Price Service that provides a method to retrieve
 * a list of prices for a given list of asset IDs.
 * The method is protected by a circuit breaker, which will call a fallback method
 * if the Position Service is unavailable
 */
@FeignClient(name = "PRICE-SERVICE", configuration = FeignConfiguration.class)
public interface PriceClient {

    @PostMapping("/api/v1/prices/assetIds")
    @ResponseBody
    @CircuitBreaker(name = "eligibilities", fallbackMethod = "fallbackGetPrices")
    List<Price> getPrices(@RequestBody List<String> assetIds);

    default List<Price> fallbackGetPrices(Throwable e) {
        throw new TechnicalException(ErrorCodeEnum.POSITION_SERVICE_UNAVAILABLE.getErrorCode(), ErrorCodeEnum.POSITION_SERVICE_UNAVAILABLE.getMessage());
    }
}
