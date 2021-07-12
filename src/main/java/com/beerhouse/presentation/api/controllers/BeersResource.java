package com.beerhouse.presentation.api.controllers;

import com.beerhouse.common.core.lang.AppException;
import com.beerhouse.domain.model.repositories.BeerRepository;
import com.beerhouse.presentation.api.dto.BeerDto;
import com.beerhouse.presentation.api.services.BeerDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("beers")
public class BeersResource {

    @Autowired
    private BeerRepository beerRepository;

    @Autowired
    private BeerDtoMapper beerDtoMapper;

    @GetMapping
    public ResponseEntity<List<BeerDto>> listBeer() {
        var beers = beerRepository.findAll().stream()
                .map(beerDtoMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(beers);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> createBeer(HttpServletRequest request, @RequestBody BeerDto beerDto) throws AppException {
        var beer = beerDtoMapper.toEntity(beerDto);
        var result = beerRepository.create(beer);
        var location = getBeerUrl(request, result.getId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Location", location)
                .build();
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<BeerDto> getBeer(@PathVariable("id") Integer id) {
        return beerRepository.findById(id)
                .map(beerDtoMapper::toDto)
                .map(beerDto -> ResponseEntity.ok().body(beerDto))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping(path = "{id}")
    @Transactional
    public ResponseEntity<?> replaceBeer(@PathVariable("id") Integer id, @RequestBody BeerDto beerDto) throws AppException {
        var beer = beerDtoMapper.toEntity(beerDto);
        beer.setId(id);
        beerRepository.replace(beer);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(path = "{id}")
    @Transactional
    public ResponseEntity<?> updateBeer(@PathVariable("id") Integer id, @RequestBody BeerDto beerDto) throws AppException {
        var beer = beerDtoMapper.toEntity(beerDto);
        beer.setId(id);
        beerRepository.update(beer);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "{id}")
    @Transactional
    public ResponseEntity<?> removeBeer(@PathVariable("id") Integer id) throws AppException {
        var hasBeer = beerRepository.existsById(id);
        if (!hasBeer) {
            throw new EntityNotFoundException();
        }
        beerRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private String getBaseUrl(HttpServletRequest request) {
        URL requestURL;
        try {
            requestURL = new URL(request.getRequestURL().toString());
        } catch (MalformedURLException ex) {
            throw new RuntimeException(ex);
        }
        String protocol = requestURL.getProtocol();
        String host = requestURL.getHost();
        String port = requestURL.getPort() == -1 ? "" : ":" + requestURL.getPort();
        String contextPath = request.getContextPath();
        return String.format("%s://%s%s%s", protocol, host, port, contextPath);
    }

    private String getBeerUrl(HttpServletRequest request) {
        return String.format("%s/beers", getBaseUrl(request));
    }

    private String getBeerUrl(HttpServletRequest request, Integer id) {
        return String.format("%s/%d", getBeerUrl(request), id);
    }
}
