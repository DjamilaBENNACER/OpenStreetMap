package io.github.joxit.pizzeria.persistence;

import io.github.joxit.pizzeria.model.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
public interface PizzaSDJDAO extends JpaRepository<Pizza, String> {

}
