package io.github.joxit.pizzeria.persistence;

import io.github.joxit.pizzeria.model.Stock;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author Jones Magloire @Joxit
 * @since 2017-11-02
 */
@Repository
// For lazy loading
@Transactional
public class StockDAO {
  @PersistenceContext
  private EntityManager em;

  public <T extends Stock> void updateStock(T stock) {
    em.merge(stock);
  }
}
