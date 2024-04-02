package com.jpmc.eligibility.repository;

import com.jpmc.eligibility.domain.Eligibility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * It includes a custom query method findByAccountIDsInAndAssetIDsIn
 * that allows you to retrieve Eligibility entities based on a list of account IDs and asset IDs
 */

@Repository
public interface EligibilityRepository extends JpaRepository<Eligibility, Long> {
    List<Eligibility> findByAccountIDsInAndAssetIDsIn(List<String> accountIDs, List<String> assetIDs);
}