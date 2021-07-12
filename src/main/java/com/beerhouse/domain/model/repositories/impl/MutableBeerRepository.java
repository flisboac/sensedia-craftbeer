package com.beerhouse.domain.model.repositories.impl;

import com.beerhouse.common.core.lang.AppException;
import com.beerhouse.domain.model.entities.Beer;

public interface MutableBeerRepository {
    Beer create(Beer beer) throws AppException;
    Beer update(Beer beer) throws AppException;
    Beer replace(Beer beer) throws AppException;
}
