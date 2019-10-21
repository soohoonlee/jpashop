package jpabook.jpashop.item;

import static javax.persistence.InheritanceType.SINGLE_TABLE;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.ManyToMany;
import jpabook.jpashop.category.Category;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Inheritance(strategy = SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
public abstract class Item {

  @Id
  @GeneratedValue
  @Column(name = "item_id")
  private Long id;

  private String name;
  private int price;
  private int stockQuantity;

  @ManyToMany(mappedBy = "items")
  private List<Category> categories = new ArrayList<>();

  /**
   * stock 증가
   */
  public void addStock(int quantity) {
    this.stockQuantity += quantity;
  }

  /**
   * stock 감소
   */
  public void removeStock(int quantity) {
    int restStock = this.stockQuantity -= quantity;
    if (restStock < 0) {
      throw new NotEnoughStockException("need more stock");
    }
  }
}
