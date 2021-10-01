package io.github.joxit.pizzeria.controller;

import io.github.joxit.pizzeria.dto.PizzaDTO;
import io.github.joxit.pizzeria.service.PizzeriaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.QueryParam;
import java.util.List;

/**
 * @author Jones Magloire @Joxit
 * @since 2017-11-02
 */
@RestController
@CrossOrigin
@RequestMapping({"/api", "/api/"})
public class PizzaController {
  private static Logger LOGGER = LoggerFactory.getLogger(PizzaController.class);
  @Autowired
  private PizzeriaService pizzeriaService;

  // Equivalent to @Get
  @RequestMapping(method = RequestMethod.GET)
  public List<PizzaDTO> getPizzeria(@QueryParam("type") String type) {
    LOGGER.info("getPizzeria");
    return pizzeriaService.getAll(type);
  }
}
