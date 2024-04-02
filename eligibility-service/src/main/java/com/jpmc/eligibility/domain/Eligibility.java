package com.jpmc.eligibility.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * Represents  the eligibility information for a list of assets and accounts.
 */

@Entity
@Table(name = "eligibilities")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Eligibility {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;
    private Boolean eligible;
    @ElementCollection
    @JsonProperty("assetIDs")
    private List<String> assetIDs;
    @ElementCollection
    @JsonProperty("accountIDs")
    private List<String> accountIDs;
    private double discount;
}
