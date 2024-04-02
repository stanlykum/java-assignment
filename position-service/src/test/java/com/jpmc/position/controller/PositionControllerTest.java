package com.jpmc.position.controller;

import com.jpmc.position.domain.Position;
import com.jpmc.position.repository.PositionRepository;
import com.jpmc.position.usecase.PositionGeneratorUsecase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
class PositionControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private PositionGeneratorUsecase positionGeneratorUsecase;

    @Test
    @DisplayName("Should return positions for given accounts")
    void shouldReturnPositionsForGivenAccounts() throws Exception {
        // given
        var accounts = Arrays.asList("account1", "account2", "account3");
        var expectedPositions = Arrays.asList(
                new Position(1L, "account1", "asset1", 100),
                new Position(2L, "account2", "asset2", 200),
                new Position(3L, "account3", "asset3", 300)
        );
        positionRepository.saveAll(expectedPositions);

        // when
        var requestBody = "[\"account1\",\"account2\",\"account3\"]";
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/positions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].accountId").value("account1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].accountId").value("account2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].accountId").value("account3"));

        // then
        var actualPositions = positionGeneratorUsecase.getPositions(accounts);
        assertThat(actualPositions).isEqualTo(expectedPositions);
    }

    @Test
    @DisplayName("Should save positions")
    void save_shouldSavePositions() throws Exception {
        // given
        var positions = Arrays.asList(
                new Position(1L, "account1", "asset1", 100),
                new Position(2L, "account2", "asset2", 200),
                new Position(3L, "account3", "asset3", 300)
        );
        String requestBody = "[{\"accountId\":\"account1\",\"assetId\":\"asset1\",\"quantity\":100}," +
                "{\"accountId\":\"account2\",\"assetId\":\"asset2\",\"quantity\":200}," +
                "{\"accountId\":\"account3\",\"assetId\":\"asset3\",\"quantity\":300}]";

        // when
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/positions/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        // then
        var savedPositions = positionRepository.findAll();
        assertThat(savedPositions).isEqualTo(positions);
    }
}