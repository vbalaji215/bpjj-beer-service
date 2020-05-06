package com.bpjj.beer.service.services;

import com.bpjj.beer.service.web.model.BeerDto;
import com.bpjj.beer.service.web.model.BeerPagedList;
import com.bpjj.beer.service.web.model.BeerStyle;
import org.springframework.data.domain.PageRequest;

import java.util.UUID;

/**
 * vbala created on 3/22/2020
 * Inside the package - com.bpjj.beer.service.services
 **/
public interface BeerService {
    BeerDto getBeerById(UUID beerId, Boolean showInventoryOnHand);

    BeerDto saveBeer(BeerDto beerDto);

    BeerDto updateBeer(UUID beerId, BeerDto beerDto);

    BeerPagedList listBeers(String beerName, BeerStyle beerStyle, PageRequest of, Boolean showInventoryOnHand);
}
