package com.jpmc.price.repository;

import com.jpmc.price.domain.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for managing price data.
 */

@Repository
public interface PriceRepository extends JpaRepository<Price, String> {

    List<Price> findByAssetIdIn(List<String> assetIds);
}