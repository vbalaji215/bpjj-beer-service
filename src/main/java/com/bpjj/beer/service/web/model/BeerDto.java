package com.bpjj.beer.service.web.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * vbala created on 3/17/2020
 * Inside the package - com.bpjj.beer.service.web.model
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BeerDto {
    @Null
    private UUID id;
    @Null
    private Integer version;
    @Null
    private OffsetDateTime createdDate;
    @Null
    private OffsetDateTime lastModifiedDate;
    @NotBlank
    private String beerName;
    @NotNull
    private BeerStyle beerStyle;
    @Positive
    @NotNull
    private Long upc;
    @Positive
    @NotNull
    private BigDecimal price;
    private Integer quantityOnHand;

}
