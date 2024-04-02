package com.jpmc.eligibility.usecase;

import com.jpmc.eligibility.domain.Eligibility;
import com.jpmc.eligibility.repository.EligibilityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of the EligibilityService interface.
 */
@Service
@RequiredArgsConstructor
public class EligibilityGeneratorUsecase implements EligibilityGenerator {

    private final EligibilityRepository eligibilityRepository;

    /**
     * Get list of Eligibility objects based on the provided account and asset IDs
     *
     * @param accountIDs
     * @param assetIDs
     * @return
     */
    @Override
    public List<Eligibility> getEligibilities(List<String> accountIDs, List<String> assetIDs) {
        return eligibilityRepository.findByAccountIDsInAndAssetIDsIn(accountIDs, assetIDs);
    }
}
