package com.bpjj.beer.service.services;

import com.bpjj.beer.service.domain.Beer;
import com.bpjj.beer.service.exception.BeerNotFoundException;
import com.bpjj.beer.service.repositories.BeerRepository;
import com.bpjj.beer.service.web.mapper.BeerMapper;
import com.bpjj.beer.service.web.model.BeerDto;
import com.bpjj.beer.service.web.model.BeerPagedList;
import com.bpjj.beer.service.web.model.BeerStyle;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;
import java.util.stream.Collectors;

/**
 * vbala created on 3/22/2020
 * Inside the package - com.bpjj.beer.service.services
 **/
@RequiredArgsConstructor
@Service
public class BeerServiceImpl implements BeerService {

    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    @Cacheable(cacheNames = "beerCache", key = "#beerId", condition = "#showInventoryOnHand == false")
    @Override
    public BeerDto getBeerById(UUID beerId, Boolean showInventoryOnHand) {
        System.out.println("I was called");
        if(showInventoryOnHand){
            return beerMapper.beerToBeerDtoWithInventory(
                    beerRepository.findById(beerId)
                            .orElseThrow(BeerNotFoundException::new));
        }else{
            return beerMapper.beerToBeerDto(
                    beerRepository.findById(beerId)
                            .orElseThrow(BeerNotFoundException::new));
        }
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

    @Cacheable(cacheNames = "beerListCache", condition = "#showInventoryOnHand == false ")
    @Override
    public BeerPagedList listBeers(String beerName, BeerStyle beerStyle, PageRequest pageRequest, Boolean showInventoryOnHand) {
        System.out.println("I was called");
        BeerPagedList beerPagedList;
        Page<Beer> beerPage;
        if(!StringUtils.isEmpty(beerName) && !StringUtils.isEmpty(beerStyle)){
            beerPage = beerRepository.findAllByBeerNameAndBeerStyle(beerName, beerStyle, pageRequest);
        }else if (!StringUtils.isEmpty(beerName)){
            beerPage = beerRepository.findAllByBeerName(beerName, pageRequest);
        }else if (!StringUtils.isEmpty(beerStyle)){
            beerPage = beerRepository.findAllByBeerStyle(beerStyle, pageRequest);
        }else{
            beerPage = beerRepository.findAll(pageRequest);
        }

        if(showInventoryOnHand){
            beerPagedList = new BeerPagedList(beerPage
                    .getContent()
                    .stream()
                    .map(beerMapper::beerToBeerDtoWithInventory)
                    .collect(Collectors.toList()),
                    PageRequest.of(
                            beerPage.getPageable().getPageNumber(),
                            beerPage.getPageable().getPageSize()
                    ),
                    beerPage.getTotalElements());
        }else{
            beerPagedList = new BeerPagedList(beerPage
                    .getContent()
                    .stream()
                    .map(beerMapper::beerToBeerDto)
                    .collect(Collectors.toList()),
                    PageRequest.of(
                            beerPage.getPageable().getPageNumber(),
                            beerPage.getPageable().getPageSize()
                    ),
                    beerPage.getTotalElements());
        }

        return beerPagedList;
    }
}
