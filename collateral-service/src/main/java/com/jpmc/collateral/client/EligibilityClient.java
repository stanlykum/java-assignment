package com.jpmc.collateral.client;

import com.jpmc.collateral.config.FeignConfiguration;
import com.jpmc.collateral.domain.Eligibility;
import com.jpmc.collateral.domain.EligibilityRequest;
import com.jpmc.collateral.exception.ErrorCodeEnum;
import com.jpmc.collateral.exception.TechnicalException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Feign client for the Eligibility Service, with a method to retrieve eligibilities.
 * The method is protected by a circuit breaker, which will call a fallback method
 * if the Eligibility Service is unavailable.
 */
@FeignClient(name = "ELIGIBILITY-SERVICE", configuration = FeignConfiguration.class)
public interface EligibilityClient {

    @PostMapping("/api/v1/eligibilities")
    @ResponseBody
    @CircuitBreaker(name = "eligibilities", fallbackMethod = "fallbackGetEligibilities")
    List<Eligibility> getEligibilities(@RequestBody EligibilityRequest eligibilityRequest);

    default List<Eligibility> fallbackGetEligibilities(Throwable e) {
        throw new TechnicalException(ErrorCodeEnum.POSITION_SERVICE_UNAVAILABLE.getErrorCode(), ErrorCodeEnum.POSITION_SERVICE_UNAVAILABLE.getMessage());
    }
}
