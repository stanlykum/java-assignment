package com.jpmc.position.usecase;

import com.jpmc.position.domain.Position;
import com.jpmc.position.repository.PositionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PositionGeneratorUsecaseTest {

    @Mock
    private PositionRepository positionRepository;

    private PositionGeneratorUsecase positionGeneratorUsecase;

    @BeforeEach
    void setUp() {
        positionGeneratorUsecase = new PositionGeneratorUsecase(positionRepository);
    }

    @Test
    @DisplayName("Should return positions for given accounts")
    void shouldReturnPositionsForGivenAccounts() {
        // given
        var accounts = Arrays.asList("account1", "account2", "account3");
        var expectedPositions = Arrays.asList(
                new Position(1L, "account1", "asset1", 100),
                new Position(2L, "account2", "asset2", 200),
                new Position(3L, "account3", "asset3", 300)
        );
        when(positionRepository.findByAccountIdIn(accounts)).thenReturn(expectedPositions);

        // when
        List<Position> actualPositions = positionGeneratorUsecase.getPositions(accounts);

        // then
        assertThat(actualPositions)
                .isEqualTo(expectedPositions)
                .hasSize(3)
                .extracting(Position::getAccountId)
                .containsExactlyInAnyOrder("account1", "account2", "account3");
    }
}