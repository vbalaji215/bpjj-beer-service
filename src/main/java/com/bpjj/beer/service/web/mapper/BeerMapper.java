package com.bpjj.beer.service.web.mapper;

import com.bpjj.beer.service.domain.Beer;
import com.bpjj.beer.service.web.model.BeerDto;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

/**
 * vbala created on 3/21/2020
 * Inside the package - com.bpjj.beer.service.web.mapper
 **/
@Mapper(uses = {DateMapper.class})
@DecoratedWith(BeerMapperDecorator.class)
public interface BeerMapper {

    BeerDto beerToBeerDto(Beer beer);
    BeerDto beerToBeerDtoWithInventory(Beer beer);
    Beer beerDtoToBeer(BeerDto beerDto);
}
