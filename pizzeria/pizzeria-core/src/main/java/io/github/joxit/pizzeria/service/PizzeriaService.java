package io.github.joxit.pizzeria.service;

import io.github.joxit.pizzeria.dto.PizzaDTO;
import io.github.joxit.pizzeria.exception.HandledException;
import io.github.joxit.pizzeria.mapper.PizzaMapper;
import io.github.joxit.pizzeria.model.Stock;
import io.github.joxit.pizzeria.persistence.PizzaDAO;
import io.github.joxit.pizzeria.persistence.PizzaSDJDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Jones Magloire @Joxit
 * @since 2017-11-01
 */
@Service
// For lazy loading
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class PizzeriaService {
  private static Logger LOGGER = LoggerFactory.getLogger(PizzeriaService.class);
  @Autowired
  private PizzaDAO pizzaDAO;
  @Autowired
  private PizzaMapper pizzaMapper;
  @Autowired
  private PizzaSDJDAO pizzaSDJDAO;

  public List<PizzaDTO> getAll(String type) {
    if ("eager".equalsIgnoreCase(type)) {
      LOGGER.info("Run getAllWithIng");
      return getAllWithIng();
    } else if ("custom".equalsIgnoreCase(type)) {
      LOGGER.info("Run getAllCustom");
      return getAllCustom();
    } else if ("errorNotHandled".equalsIgnoreCase(type)) {
      LOGGER.info("throw RuntimeException");
      throw new RuntimeException("Error Not Handled");
    } else if ("errorHandled".equalsIgnoreCase(type)) {
      LOGGER.info("throw HandledException");
      throw new HandledException("Error Handled");
    } else if ("spring-data-jpa".equalsIgnoreCase(type)) {
      LOGGER.info("Run getAllSDJ");
      return getAllSDJ();
    } else if ("empty".equalsIgnoreCase(type)) {
      LOGGER.info("Run empty");
      return new ArrayList<>();
    } else {
      LOGGER.info("Run getAll");
      return getAll();
    }
  }

  public List<PizzaDTO> getAll() {
    return pizzaDAO.getAll().stream().map(pizzaMapper::modelToDTO).collect(Collectors.toList());
  }

  public List<PizzaDTO> getAllWithIng() {
    return pizzaDAO.getEager().stream().map(pizzaMapper::modelToDTO).collect(Collectors.toList());
  }

  public List<PizzaDTO> getAllCustom() {
    return pizzaDAO.getCustom().stream().map(pizzaMapper::modelToDTO).collect(Collectors.toList());
  }

  public <T extends Stock> void updateStock(T stock) {

  }

  public List<PizzaDTO> getAllSDJ() {
    return pizzaSDJDAO.findAll().stream().map(pizzaMapper::modelToDTO).collect(Collectors.toList());
  }
}
