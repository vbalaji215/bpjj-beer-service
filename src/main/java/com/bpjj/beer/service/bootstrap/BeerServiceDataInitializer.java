package com.bpjj.beer.service.bootstrap;

import com.bpjj.beer.service.domain.Beer;
import com.bpjj.beer.service.repositories.BeerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import java.math.BigDecimal;
import java.util.UUID;

/**
 * vbala created on 3/19/2020
 * Inside the package - com.bpjj.beer.service.bootstrap
 **/
//@Component
@Slf4j
public class BeerServiceDataInitializer implements CommandLineRunner {

    public static final String BEER_1_UPC = "032345340043";
    public static final String BEER_2_UPC = "032345340044";
    public static final String BEER_3_UPC = "032345340045";
    public static final UUID BEER_1_UUID = UUID.fromString("0a818933-087d-47f2-ad83-2f986ed087eb");
    public static final UUID BEER_2_UUID = UUID.fromString("a712d914-61ea-4623-8bd0-32c0f6545bfd");
    public static final UUID BEER_3_UUID = UUID.fromString("026cc3c8-3a0c-4083-a05b-e908048c1b08");

    private final BeerRepository beerRepository;

    public BeerServiceDataInitializer(BeerRepository beerRepository) {
        this.beerRepository = beerRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadBeerObjects();
    }

    private void loadBeerObjects(){
        if(beerRepository.count() == 0){
            saveBeer("Mango Bobs",
                    "IPA",
                    12,
                    200,
                    BEER_1_UPC,
                    new BigDecimal("12.95"));
            saveBeer("Galaxy Cat",
                    "PALE_ALE",
                    12,
                    200,
                    BEER_2_UPC,
                    new BigDecimal("11.95"));
            saveBeer("No Hammers on the Bar",
                    "PALE_ALE",
                    12,
                    200,
                    BEER_3_UPC,
                    new BigDecimal("11.95"));
        }
        log.debug("Loaded Beers:" + beerRepository.count());
    }

    private void saveBeer(String beerName,
                          String beerStyle,
                          Integer minQtyOnHand,
                          Integer quantityToBrew,
                          String upc,
                          BigDecimal price){
        beerRepository.save(Beer.builder()
                .beerName(beerName)
                .beerStyle(beerStyle)
                .minQtyOnHand(minQtyOnHand)
                .quantityToBrew(quantityToBrew)
                .upc(upc)
                .price(price)
                .build());
    }
}
