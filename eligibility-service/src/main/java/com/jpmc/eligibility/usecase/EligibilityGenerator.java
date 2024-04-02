package com.jpmc.eligibility.usecase;

import com.jpmc.eligibility.domain.Eligibility;

import java.util.List;

/**
 * Responsible for generating a list of Eligibility objects based on the provided account and asset IDs.
 */
public interface EligibilityGenerator {
    List<Eligibility> getEligibilities(List<String> accountIDs, List<String> assetIDs);
}