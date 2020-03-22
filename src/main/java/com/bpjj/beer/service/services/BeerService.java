package com.bpjj.beer.service.services;

import com.bpjj.beer.service.web.model.BeerDto;

import java.util.UUID;

/**
 * vbala created on 3/22/2020
 * Inside the package - com.bpjj.beer.service.services
 **/
public interface BeerService {
    BeerDto getBeerById(UUID beerId);

    BeerDto saveBeer(BeerDto beerDto);

    BeerDto updateBeer(UUID beerId, BeerDto beerDto);
}
