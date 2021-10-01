package io.github.joxit.pizzeria.dto;

import java.util.Collection;

/**
 * @author Jones Magloire @Joxit
 * @since 2017-11-01
 */
public class PizzaDTO {

  private String name;
  private float prix;
  private Collection<String> ingredients;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public float getPrix() {
    return prix;
  }

  public void setPrix(float prix) {
    this.prix = prix;
  }

  public Collection<String> getIngredients() {
    return ingredients;
  }

  public void setIngredients(Collection<String> ingredients) {
    this.ingredients = ingredients;
  }

  @Override
  public String toString() {
    return "PizzaDTO{" +
        "name='" + name + '\'' +
        ", prix=" + prix +
        ", ingredients=" + ingredients +
        '}';
  }
}
