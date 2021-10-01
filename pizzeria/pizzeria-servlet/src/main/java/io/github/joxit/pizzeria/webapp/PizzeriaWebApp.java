package io.github.joxit.pizzeria.webapp;

import io.github.joxit.pizzeria.service.PizzeriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.ws.rs.QueryParam;

/**
 * @author Jones Magloire @Joxit
 * @since 2017-11-01
 */
@Controller
@RequestMapping({"/index.html", "/"})
public class PizzeriaWebApp {
  @Autowired
  private PizzeriaService pizzeriaService;

  @RequestMapping(method = RequestMethod.GET)
  public String getPizzeria(@QueryParam("type") String type, ModelMap model) {
    model.addAttribute("pizzas", pizzeriaService.getAll(type));
    return "index";
  }
}
