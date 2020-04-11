package com.bpjj.beer.service.services.inventory;

import com.bpjj.beer.service.bootstrap.BeerServiceDataInitializer;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by IntelliJ IDEA
 * User: Balaji Varadharajan
 * Class/Interface/Enum Name: BeerInventoryServiceRestTemplateImplTest
 * Inside the package - com.bpjj.beer.service.services.inventory
 * Created Date: 4/9/2020
 * Created Time: 10:58 PM
 **/
@Disabled
@SpringBootTest
@Slf4j
class BeerInventoryServiceRestTemplateImplTest {

    @Autowired
    BeerInventoryService beerInventoryService;

    @BeforeEach
    void setUp(){

    }

    @Test
    void getOnHandInventory() {
        Integer quantityOnHand = beerInventoryService.getOnHandInventory(BeerServiceDataInitializer.BEER_1_UUID);
        log.info("Quantity on hand: {}", quantityOnHand);
    }
}