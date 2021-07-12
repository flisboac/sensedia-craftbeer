package com.beerhouse.unit.presentation.api;

import com.beerhouse.presentation.api.dto.BeerDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

// TODO Testes para os endpoints com IDs (leitura e persistência)

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BeerResourceTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @Sql("sql/beers/ensure-single-element-collection.sql")
    public void givenNonEmptyCollection_whenListCollection_thenSucceedsAndReturnsValidList() {
        var collectionUrl = buildUrl();
        var existingBeersResponse = this.restTemplate.exchange(RequestEntity.get(collectionUrl).build(), BeerDto[].class);
        assertThat(existingBeersResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(existingBeersResponse.getBody()).isNotNull();
        assertThat(existingBeersResponse.getBody().length).isEqualTo(2);

        var existingBeers = Arrays.asList(existingBeersResponse.getBody());
        assertThat(existingBeers).isInstanceOf(List.class);

        var allValid = existingBeers.stream().allMatch(Objects::nonNull);
        assertThat(allValid).isTrue();

        var expectedBeers = Arrays.asList(generateBeer(1), generateBeer(2));
        assertThat(expectedBeers).allSatisfy(expectedBeer -> {
            var existingBeer = existingBeers.stream()
                    .filter(beer -> Objects.equals(beer.getId(), expectedBeer.getId()))
                    .findFirst()
                    .orElseThrow();
            assertThat(existingBeer).usingRecursiveComparison().isEqualTo(expectedBeer);
        });

    }

    @Test
    @Sql("sql/beers/clear-collection.sql")
    public void givenEmptyCollection_whenCreateCalledMultipleTimes_thenAllSucceedAndCollectionHasCreatedEntries() {
        var collectionUrl = buildUrl();

        IntStream.range(1, 5).forEachOrdered(expectedCollectionSize -> {
            var input = generateBeer(expectedCollectionSize);
            var createResponse = this.restTemplate.exchange(RequestEntity.post(collectionUrl).body(input), Object.class);
            assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
            assertThat(createResponse.getHeaders().containsKey("Location")).isTrue();
            var beerUrl = createResponse.getHeaders().getLocation();

            var createdBeer = this.restTemplate.getForObject(beerUrl, BeerDto.class);
            assertThat(createdBeer).isNotNull();
            assertThat(createdBeer.getId()).isNotNull();

            var createdBeerId = createdBeer.getId();
            input.setId(createdBeerId);
            assertThat(createdBeer).usingRecursiveComparison().isEqualTo(input);

            var existingBeers = Arrays.asList(this.restTemplate.getForObject(collectionUrl, BeerDto[].class));
            assertThat(existingBeers).isInstanceOf(List.class);
            assertThat(existingBeers.size()).isEqualTo(expectedCollectionSize);
            assertThat(existingBeers).anySatisfy(beer -> {
                assertThat(beer).usingRecursiveComparison().isEqualTo(createdBeer);
            });
        });
    }

    private BeerDto generateBeer(int id) {
        var beer = new BeerDto();
        var postfix = " " + id;
        beer.setId(id);
        beer.setName("a beer" + postfix);
        beer.setCategory("a beer category");
        beer.setAlcoholContent("any%");
        beer.setIngredients("beer is made of beer");
        beer.setPrice(new BigDecimal("1.23"));
        return beer;
    }

    private String buildUrl(Object... paths) {
        var path = Arrays.asList(paths).stream().map(Object::toString).collect(Collectors.joining("/"));
        return String.format("http://localhost:%d/beers/%s", port, path);
    }
}
