package io.github.joxit.pizzeria.model;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * @author Jones Magloire @Joxit
 * @since 2017-11-01
 */
@MappedSuperclass
public abstract class Stockable implements Serializable {
  @Id
  private String name;
  private float price;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public float getPrice() {
    return price;
  }

  public void setPrice(float price) {
    this.price = price;
  }

  @Override
  public String toString() {
    return "Stockable{" +
        "name='" + name + '\'' +
        ", prix=" + price +
        '}';
  }
}
