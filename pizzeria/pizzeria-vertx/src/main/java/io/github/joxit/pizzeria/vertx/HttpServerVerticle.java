package io.github.joxit.pizzeria.vertx;

import io.github.joxit.pizzeria.dto.PizzaDTO;
import io.github.joxit.pizzeria.exception.HandledException;
import io.github.joxit.pizzeria.service.PizzeriaService;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CorsHandler;
import io.vertx.ext.web.handler.TimeoutHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

@Component
@Scope(SCOPE_PROTOTYPE)
public class HttpServerVerticle extends AbstractVerticle {
  private static final Logger LOGGER = LoggerFactory.getLogger(PizzaVerticle.class);

  private static final long TIMEOUT = 30000;
  @Value("${server.port:8080}")
  private int port;

  @Autowired
  private PizzeriaService pizzeriaService;

  @Override
  public void start(Promise<Void> startFuture) {
    LOGGER.info("Start Http Server Verticle");
    Router router = Router.router(vertx);

    CorsHandler corsHandler = CorsHandler.create("*")
        .allowedMethod(HttpMethod.GET)
        .allowedMethod(HttpMethod.POST)
        .allowedMethod(HttpMethod.PUT)
        .allowedMethod(HttpMethod.PATCH)
        .allowedMethod(HttpMethod.DELETE)
        .allowedMethod(HttpMethod.OPTIONS)
        .allowedHeader(HttpHeaders.AUTHORIZATION.toString())
        .allowedHeader(HttpHeaders.CONTENT_TYPE.toString());
    router.route().handler(corsHandler);

    router.route().handler(TimeoutHandler.create(TIMEOUT));

    BodyHandler bodyHandler = BodyHandler.create().setBodyLimit(1024 * 1024);
    router.route().handler(bodyHandler);
    router.get("/api")
        .produces("application/json")
        .handler(this::handle)
        .failureHandler(ctx -> {
          Throwable err = ctx.failure();
          LOGGER.error(err.getMessage());
          if (err instanceof HandledException) {
            ctx.response().setStatusCode(400).end(err.getMessage());
          } else {
            ctx.next();
          }
        });

    HttpServerOptions options = new HttpServerOptions()
        .setHost("0.0.0.0")
        .setPort(port);

    vertx.createHttpServer(options)
        .requestHandler(router)
        .listen(res -> {
          if (res.succeeded()) {
            LOGGER.info("Vertex node started");
            startFuture.complete();
          } else {
            LOGGER.info("Vertex node failed to start");
            startFuture.fail(res.cause());
          }
        });
  }

  private void handle(RoutingContext ctx) {
    HttpServerRequest request = ctx.request();
    HttpServerResponse response = ctx.response();

    response.putHeader("Content-Type", "application/json")
        .setStatusCode(200)
        .end(Json.encode(pizzeriaService.getAll(request.getParam("type"))));
  }

  private void handleWithEventBus(RoutingContext ctx) {
    HttpServerRequest request = ctx.request();
    HttpServerResponse response = ctx.response();

    EventBus eb = ctx.vertx().eventBus();

    eb.<List<PizzaDTO>>request(PizzaVerticle.name, request.getParam("type"), ar -> {
      if (ar.failed()) {
        LOGGER.error("Error in pizzeria", ar.cause());
        ctx.fail(500);
      } else {
        response.putHeader("Content-Type", "application/json")
            .setStatusCode(200)
            .end(Json.encode(ar.result().body()));
      }
    });
  }
}
