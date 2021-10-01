package io.github.joxit.pizzeria.vertx;

import com.jolbox.bonecp.BoneCPDataSource;
import io.netty.util.ResourceLeakDetector;
import io.vertx.core.*;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.MessageCodec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@PropertySource(value = {"classpath:application.properties", "application.properties", "/conf/application.properties"}, ignoreResourceNotFound = true)
@EnableJpaRepositories(basePackages = {"io.github.joxit.pizzeria.persistence"})
@ComponentScan({"io.github.joxit.pizzeria.persistence", "io.github.joxit.pizzeria.service", "io.github.joxit.pizzeria.mapper", "io.github.joxit.pizzeria.vertx"})
@EnableTransactionManagement
@Configuration
public class VertexExample {
  private static final Logger LOGGER = LoggerFactory.getLogger(VertexExample.class);

  @Value("${dataSource.username}")
  private String dataSourceUsername;
  @Value("${dataSource.jdbcUrl}")
  private String jdbcUrl;
  @Value("${dataSource.password}")
  private String dataSourcePassword;

  @Autowired
  private Vertx vertx;
  @Autowired
  private SpringVerticleFactory verticleFactory;

  public static void main(String[] args) {
    new AnnotationConfigApplicationContext(VertexExample.class);
  }

  @Bean
  public DataSource dataSource() {
    final BoneCPDataSource dataSource = new BoneCPDataSource();
    dataSource.setDriverClass("com.mysql.cj.jdbc.Driver");
    dataSource.setJdbcUrl("jdbc:" + jdbcUrl);
    dataSource.setUsername(dataSourceUsername);
    dataSource.setPassword(dataSourcePassword);
    return dataSource;
  }

  @Bean
  @Autowired
  public JdbcTemplate jdbcTemplate(DataSource dataSource) {
    return new JdbcTemplate(dataSource);
  }

  @Bean
  @Autowired
  public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
    final LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
    factory.setDataSource(dataSource);
    HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
    factory.setDataSource(dataSource);
    factory.setJpaVendorAdapter(vendorAdapter);
    factory.setPackagesToScan("io.github.joxit.pizzeria.model");
    Properties jpaProperties = new Properties();
    jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
    factory.setJpaProperties(jpaProperties);
    return factory;
  }

  @Bean
  @Autowired
  public PlatformTransactionManager transactionManager(LocalContainerEntityManagerFactoryBean entityManagerFactory) {
    return new JpaTransactionManager(entityManagerFactory.getNativeEntityManagerFactory());
  }

  @PostConstruct
  private void deployVerticle() {
    ResourceLeakDetector.setLevel(ResourceLeakDetector.Level.DISABLED);
    vertx.registerVerticleFactory(verticleFactory);

    EventBus eventBus = vertx.eventBus();

    List<Future> futures = new ArrayList<>();

    // Scale the verticles on cores
    int cores = Runtime.getRuntime().availableProcessors();
    DeploymentOptions options = new DeploymentOptions().setInstances(cores);
    Promise<String> promise = Promise.promise();
    futures.add(promise.future());
    vertx.deployVerticle(verticleFactory.prefix() + ":" + HttpServerVerticle.class.getName(), options, promise);

    promise = Promise.promise();
    futures.add(promise.future());
    vertx.deployVerticle(verticleFactory.prefix() + ":" + PizzaVerticle.class.getName(), promise);

    eventBus.registerDefaultCodec(ArrayList.class, ObjectMessageCodec.of(ArrayList.class));

    CompositeFuture.all(futures).onComplete(ar -> {
      if (ar.succeeded()) {
        LOGGER.info("Pizzeria app is started");
      } else {
        LOGGER.error("Error in server initialization", ar.cause());
        System.exit(1);
      }
    });
  }

  @Bean
  public Vertx vertx() {
    return Vertx.vertx();
  }

  private static class ObjectMessageCodec<T> implements MessageCodec<T, T> {

    private final Class<T> clazz;

    /**
     * Constructs a new ObjectMessageCodec.
     *
     * @param clazz the class
     * @param <T>   the type of the class
     * @return a new instance
     */
    private static <T> ObjectMessageCodec<T> of(Class<T> clazz) {
      return new ObjectMessageCodec<>(clazz);
    }

    /**
     * Constructs a new ObjectMessageCodec from a class.
     *
     * @param clazz the class
     */
    private ObjectMessageCodec(Class<T> clazz) {
      this.clazz = clazz;
    }

    @Override
    public void encodeToWire(Buffer buffer, T msg) {
      // TODO implements
      throw new IllegalStateException("Not implemented");
    }

    @Override
    public T decodeFromWire(int pos, Buffer buffer) {
      // TODO implements
      throw new IllegalStateException("Not implemented");
    }

    @Override
    public T transform(T msg) {
      return msg;
    }

    @Override
    public String name() {
      return this.getClass().getSimpleName() + "<" + clazz.getSimpleName() + ">";
    }

    @Override
    public byte systemCodecID() {
      return -1;
    }
  }
}
