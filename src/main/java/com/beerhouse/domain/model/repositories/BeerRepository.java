package com.beerhouse.domain.model.repositories;

import com.beerhouse.domain.model.entities.Beer;
import com.beerhouse.domain.model.repositories.impl.MutableBeerRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BeerRepository extends JpaRepository<Beer, Integer>, MutableBeerRepository {
}
