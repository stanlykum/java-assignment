package com.jpmc.price.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpmc.price.domain.Price;
import com.jpmc.price.repository.PriceRepository;
import com.jpmc.price.usecase.PriceGeneratorUsecase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PriceController.class)
class PriceControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PriceGeneratorUsecase priceGeneratorUsecase;

    @MockBean
    private PriceRepository priceRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Should get prices")
    void shouldGetPrices() throws Exception {
        // given
        var assetIds = Arrays.asList("S1", "S2", "S3");
        var prices = Arrays.asList(
                Price.builder().assetId("S1").price(50.5).build(),
                Price.builder().assetId("S2").price(20.2).build(),
                Price.builder().assetId("S3").price(10.4).build()
        );

        when(priceGeneratorUsecase.getPrices(assetIds)).thenReturn(prices);

        // when / then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/prices/assetIds")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(assetIds)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].assetId").value("S1"))
                .andExpect(jsonPath("$[0].price").value(50.5))
                .andExpect(jsonPath("$[1].assetId").value("S2"))
                .andExpect(jsonPath("$[1].price").value(20.2))
                .andExpect(jsonPath("$[2].assetId").value("S3"))
                .andExpect(jsonPath("$[2].price").value(10.4));
    }

    @Test
    @DisplayName("Should save prices")
    void shouldSavePrices() throws Exception {
        // given
        var prices = Arrays.asList(
                Price.builder().assetId("S1").price(50.5).build(),
                Price.builder().assetId("S2").price(20.2).build(),
                Price.builder().assetId("S3").price(10.4).build()
        );

        // when / then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/prices/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(prices)))
                .andExpect(status().isCreated());

        verify(priceRepository).saveAllAndFlush(prices);
    }

}