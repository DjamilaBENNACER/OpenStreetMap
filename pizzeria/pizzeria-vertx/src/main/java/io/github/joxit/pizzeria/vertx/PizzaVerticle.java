package io.github.joxit.pizzeria.vertx;


import io.github.joxit.pizzeria.service.PizzeriaService;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

@Component
@Scope(SCOPE_PROTOTYPE)
public class PizzaVerticle extends AbstractVerticle {
  private static final Logger LOGGER = LoggerFactory.getLogger(PizzaVerticle.class);
  public static final String name = "PizzaVerticle";

  @Autowired
  private PizzeriaService pizzeriaService;

  @Override
  public void start(Promise<Void> startFuture) {
    LOGGER.info("Start Pizza Verticle");
    EventBus eb = vertx.eventBus();
    eb.localConsumer(name, this::handle);
  }

  private void handle(Message<String> msg) {
    Future.future(ar -> ar.complete(pizzeriaService.getAll(msg.body())))
        .onComplete(ar -> msg.reply(ar.result()))
        .otherwise(err -> {
          msg.fail(500, err.getMessage());
          return err.getMessage();
        });
  }

}
