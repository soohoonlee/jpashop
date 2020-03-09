package jpabook.jpashop.api;

import java.util.List;
import jpabook.jpashop.order.Order;
import jpabook.jpashop.order.OrderItem;
import jpabook.jpashop.order.OrderRepository;
import jpabook.jpashop.order.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

  private final OrderRepository orderRepository;

  @GetMapping("/api/v1/orders")
  public List<Order> ordersV1() {
    List<Order> all = orderRepository.findAllByString(new OrderSearch());
    for (Order order : all) {
      order.getMember().getName();
      order.getDelivery().getAddress();

      List<OrderItem> orderItems = order.getOrderItems();
      orderItems.stream().forEach(orderItem -> orderItem.getItem().getName());
    }
    return all;
  }
}
