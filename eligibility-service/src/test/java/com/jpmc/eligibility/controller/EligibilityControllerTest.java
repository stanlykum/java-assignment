package com.jpmc.eligibility.controller;

import com.jpmc.eligibility.domain.Eligibility;
import com.jpmc.eligibility.domain.EligibilityRequest;
import com.jpmc.eligibility.repository.EligibilityRepository;
import com.jpmc.eligibility.usecase.EligibilityGeneratorUsecase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EligibilityController.class)
class EligibilityControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EligibilityGeneratorUsecase eligibilityGeneratorUsecase;

    @MockBean
    private EligibilityRepository eligibilityRepository;

    @Test
    @DisplayName("Should get eligibilities by accountIDs and assetIDs")
    void shouldGetEligibilities() throws Exception {
        // given
        var eligibilityRequest = EligibilityRequest.builder().accountIDs(Arrays.asList("account1", "account2", "account3"))
                .assetIDs(Arrays.asList("asset1", "asset2", "asset3")).build();

        var eligibilities = Arrays.asList(
                new Eligibility(1L, true, Arrays.asList("asset1", "asset2"), Arrays.asList("account1", "account2"), 0.2),
                new Eligibility(2L, false, Arrays.asList("asset3"), Arrays.asList("account3"), 0.1)
        );

        when(eligibilityGeneratorUsecase.getEligibilities(eligibilityRequest.getAccountIDs(), eligibilityRequest.getAssetIDs()))
                .thenReturn(eligibilities);

        // when / then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/eligibilities")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"accountIDs\":[\"account1\",\"account2\",\"account3\"],\"assetIDs\":[\"asset1\",\"asset2\",\"asset3\"]}")
        )
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Should save eligibilities")
    void shouldSaveEligibilities() throws Exception {

        // when / then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/eligibilities/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content("[{\"id\":1,\"eligible\":1,\"assetIDs\":[\"asset1\",\"asset2\"],\"accountIDs\":[\"account1\",\"account2\"],\"discount\":0.2},{\"id\":2,\"eligible\":0,\"assetIDs\":[\"asset3\"],\"accountIDs\":[\"account3\"],\"discount\":0.1}]")
        )
                .andExpect(status().isCreated());
    }
}
