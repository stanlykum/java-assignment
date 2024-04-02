package com.jpmc.collateral.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpmc.collateral.domain.CollateralValue;
import com.jpmc.collateral.usecase.CollateralServiceUsecase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CollateralController.class)
class CollateralControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CollateralServiceUsecase collateralServiceUsecase;

    @Test
    @DisplayName("Should calculate collateral value")
    void shouldcalculateCollateralValue() throws Exception {
        // given
        List<String> accountIds = Arrays.asList("account1", "account2", "account3");
        List<CollateralValue> collateralValues = Arrays.asList(
                new CollateralValue("account1", new BigDecimal("1000.0")),
                new CollateralValue("account2", new BigDecimal("2000.0")),
                new CollateralValue("account3", new BigDecimal("3000.0"))
        );
        when(collateralServiceUsecase.calculateCollateralValue(accountIds)).thenReturn(collateralValues);

        // when / then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/collaterals/calculate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(accountIds)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(collateralValues)));
    }
}