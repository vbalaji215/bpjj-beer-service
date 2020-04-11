package com.bpjj.beer.service.web.mapper;

import com.bpjj.beer.service.domain.Beer;
import com.bpjj.beer.service.services.inventory.BeerInventoryService;
import com.bpjj.beer.service.web.model.BeerDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by IntelliJ IDEA
 * User: Balaji Varadharajan
 * Class/Interface/Enum Name: BeerMapperDecorator
 * Inside the package - com.bpjj.beer.service.web.mapper
 * Created Date: 4/9/2020
 * Created Time: 10:46 PM
 **/
@Slf4j
public abstract class BeerMapperDecorator implements BeerMapper {

    private BeerInventoryService beerInventoryService;
    private BeerMapper mapper;

    @Autowired
    public void setBeerInventoryService(BeerInventoryService beerInventoryService){
        this.beerInventoryService = beerInventoryService;
    }

    @Autowired
    public void setMapper(BeerMapper beerMapper){
        this.mapper = beerMapper;
    }

    public BeerDto beerToBeerDto(Beer beer){
        log.info("Beer Id:" + beer.getBeerId());
        BeerDto beerDto = mapper.beerToBeerDto(beer);
        log.info("Beer Dto Id:" + beerDto.getBeerId());
        beerDto.setQuantityOnHand(beerInventoryService.getOnHandInventory(beer.getBeerId()));
        return  beerDto;
    }

    public Beer beerDtoToBeer(BeerDto beerDto){
        return mapper.beerDtoToBeer(beerDto);
    }
}
