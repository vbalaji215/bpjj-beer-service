package com.bpjj.beer.service.services.inventory;

import java.util.UUID;

/**
 * Created by IntelliJ IDEA
 * User: Balaji Varadharajan
 * Class/Interface/Enum Name: BeerInventoryService
 * Inside the package - com.bpjj.beer.service.services.inventory
 * Created Date: 4/9/2020
 * Created Time: 10:29 PM
 **/
public interface BeerInventoryService {

    Integer getOnHandInventory(UUID beerId);
}
