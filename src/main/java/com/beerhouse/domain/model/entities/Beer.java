package com.beerhouse.domain.model.entities;

import com.beerhouse.common.entities.validationGroups.Create;
import com.beerhouse.common.entities.validationGroups.Replace;
import com.beerhouse.common.entities.validationGroups.Update;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Entity
@SequenceGenerator(name = "sq_beer", sequenceName = "sq_beer", allocationSize = 1)
public class Beer {
    @Id
    @GeneratedValue(generator = "sq_beer", strategy = GenerationType.SEQUENCE)
    @NotNull(message = "{model.entities.beer.id.notNull}", groups = { Update.class, Replace.class })
    private Integer id;

    @NotNull(message = "{model.entities.beer.name.notNull}", groups = { Create.class, Replace.class })
    @Size(min = 1, max = 128, message = "{model.entities.beer.name.length}")
    @Pattern(regexp = ".*\\S+.*", message = "{model.entities.beer.name.notBlank}")
    private String name;

    @NotNull(message = "{model.entities.beer.ingredients.notNull}", groups = { Create.class, Replace.class })
    @Size(min = 1, max = 512, message = "{model.entities.beer.ingredients.length}")
    @Pattern(regexp = ".*\\S+.*", message = "{model.entities.beer.ingredients.notBlank}")
    private String ingredients;

    @NotNull(message = "{model.entities.beer.alcoholContent.notNull}", groups = { Create.class, Replace.class })
    @Size(min = 1, max = 64, message = "{model.entities.beer.alcoholContent.length}")
    @Pattern(regexp = ".*\\S+.*", message = "{model.entities.beer.alcoholContent.notBlank}")
    private String alcoholContent;

    @NotNull(message = "{model.entities.beer.price.notNull}", groups = { Create.class, Replace.class })
    @PositiveOrZero(message = "{model.entities.beer.price.positiveOrZero")
    private BigDecimal price;

    @NotNull(message = "{model.entities.beer.category.notNull}", groups = { Create.class, Replace.class })
    @Size(min = 1, max = 128, message = "{model.entities.beer.category.length}")
    @Pattern(regexp = ".*\\S+.*", message = "{model.entities.beer.category.notBlank}")
    private String category;

    @NotNull(groups = Create.class)
    private ZonedDateTime createdAt;

    @NotNull(groups = Update.class)
    private ZonedDateTime updatedAt;

    public static Beer fromId(Integer id) {
        Beer beer = new Beer();
        beer.setId(id);
        return beer;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getAlcoholContent() {
        return alcoholContent;
    }

    public void setAlcoholContent(String alcoholContent) {
        this.alcoholContent = alcoholContent;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ZonedDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(ZonedDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
