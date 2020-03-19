package com.bpjj.beer.service.repositories;

import com.bpjj.beer.service.domain.Beer;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * vbala created on 3/19/2020
 * Inside the package - com.bpjj.beer.service.repositories
 **/
@Repository
public interface BeerRepository extends PagingAndSortingRepository<Beer, UUID> {

}
