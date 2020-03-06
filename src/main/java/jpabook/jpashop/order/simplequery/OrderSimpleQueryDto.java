package jpabook.jpashop.order.simplequery;

import java.time.LocalDateTime;
import jpabook.jpashop.member.Address;
import jpabook.jpashop.order.OrderStatus;
import lombok.Data;

@Data
public class OrderSimpleQueryDto {

  private Long orderId;
  private String name;
  private LocalDateTime orderDate;
  private OrderStatus orderStatus;
  private Address address;

  public OrderSimpleQueryDto(Long orderId, String name, LocalDateTime orderDate,
      OrderStatus orderStatus, Address address) {
    this.orderId = orderId;
    this.name = name;
    this.orderDate = orderDate;
    this.orderStatus = orderStatus;
    this.address = address;
  }
}