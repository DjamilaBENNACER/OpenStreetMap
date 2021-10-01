package io.github.joxit.pizzeria.model;

import java.util.Collection;

/**
 * @author Jones Magloire @Joxit
 * @since 2017-11-01
 */
public class PizzaCustom extends Stockable {
  private Collection<Ingredient> ingredients;

  public Collection<Ingredient> getIngredients() {
    return ingredients;
  }

  public void setIngredients(Collection<Ingredient> ingredients) {
    this.ingredients = ingredients;
  }

  @Override
  public String toString() {
    return "PizzaCustom{" +
        "ingredients=" + ingredients + ", " + super.toString() +
        '}';
  }
}
