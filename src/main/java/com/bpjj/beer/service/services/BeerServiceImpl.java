package com.bpjj.beer.service.services;

import com.bpjj.beer.service.domain.Beer;
import com.bpjj.beer.service.exception.BeerNotFoundException;
import com.bpjj.beer.service.repositories.BeerRepository;
import com.bpjj.beer.service.web.mapper.BeerMapper;
import com.bpjj.beer.service.web.model.BeerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * vbala created on 3/22/2020
 * Inside the package - com.bpjj.beer.service.services
 **/
@RequiredArgsConstructor
@Service
public class BeerServiceImpl implements BeerService {

    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    @Override
    public BeerDto getBeerById(UUID beerId) {
        return beerMapper.beerToBeerDto(
                beerRepository.findById(beerId)
                        .orElseThrow(BeerNotFoundException::new));
    }

    @Override
    public BeerDto saveBeer(BeerDto beerDto) {
        Beer beer = beerRepository.save(beerMapper.beerDtoToBeer(beerDto));
        BeerDto createdBeer = beerMapper.beerToBeerDto(beer);
        return createdBeer;
    }

    @Override
    public BeerDto updateBeer(UUID beerId, BeerDto beerDto) {
        Beer beer = beerRepository.findById(beerId)
                        .orElseThrow(BeerNotFoundException::new);
        beer = beerRepository.save(beer);
        BeerDto updatedBeer = beerMapper.beerToBeerDto(beer);
        return updatedBeer;
    }
}
