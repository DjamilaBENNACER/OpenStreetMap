package io.github.joxit.pizzeria.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Collection;

/**
 * @author Jones Magloire @Joxit
 * @since 2017-11-01
 */
@Entity
@Table(name = "Pizza")
public class PizzaEager extends Stockable {
  @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JoinTable(name = "MtM_Pizza_Ingredient",
      joinColumns = {@JoinColumn(name = "pizza", referencedColumnName = "name")},
      inverseJoinColumns = {@JoinColumn(name = "ingredient", referencedColumnName = "name")})
  private Collection<Ingredient> ingredients;

  public Collection<Ingredient> getIngredients() {
    return ingredients;
  }

  public void setIngredients(Collection<Ingredient> ingredients) {
    this.ingredients = ingredients;
  }

  @Override
  public String toString() {
    return "PizzaEager{" +
        "ingredients=" + ingredients + ", " + super.toString() +
        '}';
  }
}
