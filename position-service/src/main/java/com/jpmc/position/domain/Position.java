package com.jpmc.position.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

/**
 * Represents a position for an account.
 */

@Data
@Entity
@Table(name = "positions")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Position {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;
    private String accountId;
    private String assetId;
    private int quantity;
}
