package com.jpmc.collateral.client;

import com.jpmc.collateral.config.FeignConfiguration;
import com.jpmc.collateral.domain.Position;
import com.jpmc.collateral.exception.ErrorCodeEnum;
import com.jpmc.collateral.exception.TechnicalException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Feign client for the Eligibility Service that provides a method to retrieve
 * a list of positions for a given list of account IDs.
 * The method is protected by a circuit breaker, which will call a fallback method
 * if the Position Service is unavailable
 */
@FeignClient(name = "POSITION-SERVICE", configuration = FeignConfiguration.class)
public interface PositionClient {

    @PostMapping("/api/v1/positions")
    @ResponseBody
    @CircuitBreaker(name = "positions", fallbackMethod = "falbackGetPositions")
    List<Position> getPositions(@RequestBody List<String> accounts);

    default List<Position> falbackGetPositions(Throwable e) {
        throw new TechnicalException(ErrorCodeEnum.POSITION_SERVICE_UNAVAILABLE.getErrorCode(), ErrorCodeEnum.POSITION_SERVICE_UNAVAILABLE.getMessage());
    }
}
