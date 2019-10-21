package jpabook.jpashop.order;

import static javax.persistence.FetchType.LAZY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import jpabook.jpashop.item.Item;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class OrderItem {

  @Id
  @GeneratedValue
  @Column(name = "order_item_id")
  private Long id;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "item_id")
  private Item item;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "order_id")
  private Order order;

  private int orderPrice;

  private int count;

  public static OrderItem createOrderItem(Item item, int orderPrice, int count) {
    OrderItem orderItem = new OrderItem();
    orderItem.setItem(item);
    orderItem.setOrderPrice(orderPrice);
    orderItem.setCount(count);
    item.removeStock(count);
    return orderItem;
  }

  public void cancel() {
    getItem().addStock(count);
  }

  /**
   * 주문상품 전체 가격 조회
   */
  public int getTotalPrice() {
    return getOrderPrice() * getCount();
  }
}
