package com.jpmc.eligibility.usecase;

import com.jpmc.eligibility.domain.Eligibility;
import com.jpmc.eligibility.repository.EligibilityRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EligibilityGeneratorUsecaseTest {

    @Mock
    private EligibilityRepository eligibilityRepository;

    @InjectMocks
    private EligibilityGeneratorUsecase eligibilityGeneratorUsecase;

    @Test
    @DisplayName("Should get eligibilities")
    void shouldGetEligibilities() {
        // given
        var accountIDs = Arrays.asList("account1", "account2", "account3");
        var assetIDs = Arrays.asList("asset1", "asset2", "asset3");

        var expectedEligibilities = Arrays.asList(
                new Eligibility(1L, true, Arrays.asList("asset1", "asset2"), Arrays.asList("account1", "account2"), 0.2),
                new Eligibility(2L, false, Arrays.asList("asset3"), Arrays.asList("account3"), 0.1)
        );

        when(eligibilityRepository.findByAccountIDsInAndAssetIDsIn(accountIDs, assetIDs))
                .thenReturn(expectedEligibilities);

        // when
        var actualEligibilities = eligibilityGeneratorUsecase.getEligibilities(accountIDs, assetIDs);

        // then
        assertThat(actualEligibilities)
                .hasSize(2)
                .containsExactlyInAnyOrder(expectedEligibilities.toArray(new Eligibility[0]));
    }
}

