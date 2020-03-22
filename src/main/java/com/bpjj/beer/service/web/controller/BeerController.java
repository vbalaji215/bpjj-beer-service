package com.bpjj.beer.service.web.controller;

import com.bpjj.beer.service.services.BeerService;
import com.bpjj.beer.service.web.model.BeerDto;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
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

    private final BeerService beerService;

    @GetMapping("/{beerId}")
    public ResponseEntity<BeerDto> getBeerById(@PathVariable("beerId") UUID beerId){
        BeerDto beerDto = beerService.getBeerById(beerId);
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
