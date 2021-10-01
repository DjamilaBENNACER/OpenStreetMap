package io.github.joxit.pizzeria.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Collection;

/**
 * @author Jones Magloire @Joxit
 * @since 2017-11-01
 */
@Entity
@Table
public class Ingredient extends Stockable {

  @ManyToMany(fetch = FetchType.LAZY, mappedBy = "ingredients")
  private Collection<Pizza> pizzas;
}
