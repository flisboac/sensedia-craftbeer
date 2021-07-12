package com.beerhouse.presentation.api.services;

import com.beerhouse.presentation.api.dto.BeerDto;
import com.beerhouse.domain.model.entities.Beer;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BeerDtoMapper {
    BeerDto toDto(Beer beer);
    Beer toEntity(BeerDto beer);
}
