package com.bpjj.beer.service.web.controller;

import com.bpjj.beer.service.services.BeerService;
import com.bpjj.beer.service.web.model.BeerDto;
import com.bpjj.beer.service.web.model.BeerPagedList;
import com.bpjj.beer.service.web.model.BeerStyle;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

/**
 * vbala created on 3/17/2020
 * Inside the package - com.bpjj.beer.service.controller
 **/
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/beer")
public class BeerController {

    private static final Integer DEFAULT_PAGE_NUMBER = 0;
    private static final Integer DEFAULT_PAGE_SIZE = 25;

    private final BeerService beerService;

    @GetMapping(produces = {"application/json"})
    public ResponseEntity<BeerPagedList> listBeers(@RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                                   @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                                   @RequestParam(value = "beerName", required = false) String beerName,
                                                   @RequestParam(value = "beerStyle", required = false) BeerStyle beerStyle,
                                                   @RequestParam (value = "showInventoryOnHand", required = false) Boolean showInventoryOnHand){
        if(showInventoryOnHand == null){
            showInventoryOnHand = false;
        }

        if(pageNumber == null || pageNumber < 0){
            pageNumber = DEFAULT_PAGE_NUMBER;
        }

        if(pageSize == null || pageSize < 0){
            pageSize = DEFAULT_PAGE_SIZE;
        }

        BeerPagedList beerList = beerService.listBeers(beerName, beerStyle, PageRequest.of(pageNumber, pageSize), showInventoryOnHand);
        return new ResponseEntity<>(beerList, HttpStatus.OK);
    }

    @GetMapping("/{beerId}")
    public ResponseEntity<BeerDto> getBeerById(@PathVariable("beerId") UUID beerId,
                                               @RequestParam (value = "showInventoryOnHand", required = false) Boolean showInventoryOnHand){
        if(showInventoryOnHand == null){
            showInventoryOnHand = false;
        }
        BeerDto beerDto = beerService.getBeerById(beerId, showInventoryOnHand);
        return new ResponseEntity<>(BeerDto.builder().build(), HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void saveNewBeer(@Valid @RequestBody BeerDto beerDto){
        beerService.saveBeer(beerDto);

    }

    @PutMapping("/{beerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateBeer(@PathVariable("beerId") UUID beerId, @Valid @RequestBody BeerDto beerDto){
        beerService.updateBeer(beerId,beerDto);

    }
}
