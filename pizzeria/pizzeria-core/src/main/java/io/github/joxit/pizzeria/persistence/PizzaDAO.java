package io.github.joxit.pizzeria.persistence;

import com.google.common.collect.Lists;
import io.github.joxit.pizzeria.model.Ingredient;
import io.github.joxit.pizzeria.model.Pizza;
import io.github.joxit.pizzeria.model.PizzaCustom;
import io.github.joxit.pizzeria.model.PizzaEager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Jones Magloire @Joxit
 * @since 2017-11-01
 */
@Repository
// For lazy loading
@Transactional
public class PizzaDAO {
  // From JPA
  @PersistenceContext
  private EntityManager em;
  @Autowired
  private JdbcTemplate jdbcTemplate;

  public List<Pizza> getAll() {
    CriteriaQuery<Pizza> cq = em.getCriteriaBuilder().createQuery(Pizza.class);
    cq.select(cq.from(Pizza.class));
    return em.createQuery(cq).getResultList();
  }

  public List<PizzaEager> getEager() {
    CriteriaQuery<PizzaEager> cq = em.getCriteriaBuilder().createQuery(PizzaEager.class);
    cq.select(cq.from(PizzaEager.class));
    return em.createQuery(cq).getResultList();
  }

  public List<PizzaCustom> getCustom() {
    return new ArrayList<>(jdbcTemplate.query("select p.name, p.price, i.name, i.price from Pizza p inner join MtM_Pizza_Ingredient m on p.name = m.pizza inner join Ingredient i on m.ingredient = i.name", (rs, rowNum) -> {
      PizzaCustom p = new PizzaCustom();
      p.setName(rs.getString("p.name"));
      p.setPrice(rs.getFloat("p.price"));
      String iName = rs.getString("i.name");
      if (iName != null) {
        Ingredient i = new Ingredient();
        i.setName(iName);
        i.setPrice(rs.getFloat("i.price"));
        p.setIngredients(Lists.newArrayList(i));
      } else {
        p.setIngredients(Lists.newArrayList());
      }
      return new SimpleImmutableEntry<>(p.getName(), p);
    }).stream().collect(Collectors.toMap(SimpleImmutableEntry::getKey, SimpleImmutableEntry::getValue, (p1, p2) -> {
      p1.getIngredients().addAll(p2.getIngredients());
      return p1;
    })).values());
  }

  public Pizza get(String name) {
    return em.find(Pizza.class, name);
  }
}
