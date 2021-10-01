package io.github.joxit.pizzeria.mapper;

import io.github.joxit.pizzeria.dto.PizzaDTO;
import io.github.joxit.pizzeria.model.Ingredient;
import io.github.joxit.pizzeria.model.Pizza;
import io.github.joxit.pizzeria.model.PizzaCustom;
import io.github.joxit.pizzeria.model.PizzaEager;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Jones Magloire @Joxit
 * @since 2017-11-01
 */
@Component
public class PizzaMapper {
  public PizzaDTO modelToDTO(Pizza pizza) {
    PizzaDTO pizzaDTO = new PizzaDTO();
    pizzaDTO.setName(pizza.getName());
    pizzaDTO.setPrix(pizza.getPrice());
    List<String> list = new ArrayList<>();
    for (Ingredient ingredient : pizza.getIngredients()) {
      String name = ingredient.getName();
      list.add(name);
    }
    pizzaDTO.setIngredients(list);
    return pizzaDTO;
  }

  public PizzaDTO modelToDTO(PizzaEager pizza) {
    PizzaDTO pizzaDTO = new PizzaDTO();
    pizzaDTO.setName(pizza.getName());
    pizzaDTO.setPrix(pizza.getPrice());
    pizzaDTO.setIngredients(pizza.getIngredients().stream().map(Ingredient::getName).collect(Collectors.toList()));
    return pizzaDTO;
  }

  public PizzaDTO modelToDTO(PizzaCustom pizza) {
    PizzaDTO pizzaDTO = new PizzaDTO();
    pizzaDTO.setName(pizza.getName());
    pizzaDTO.setPrix(pizza.getPrice());
    pizzaDTO.setIngredients(pizza.getIngredients().stream().map(Ingredient::getName).collect(Collectors.toList()));
    return pizzaDTO;
  }
}
