package com.bpjj.beer.service.services;

import com.bpjj.beer.service.domain.Beer;
import com.bpjj.beer.service.exception.BeerNotFoundException;
import com.bpjj.beer.service.repositories.BeerRepository;
import com.bpjj.beer.service.web.mapper.BeerMapper;
import com.bpjj.beer.service.web.model.BeerDto;
import com.bpjj.beer.service.web.model.BeerPagedList;
import com.bpjj.beer.service.web.model.BeerStyle;
import lombok.RequiredArgsConstructor;
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

    @Override
    public BeerPagedList listBeers(String beerName, BeerStyle beerStyle, PageRequest pageRequest) {

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
        return beerPagedList;
    }
}
