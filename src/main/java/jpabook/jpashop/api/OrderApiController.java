package jpabook.jpashop.api;

import static java.util.stream.Collectors.toList;

import java.time.LocalDateTime;
import java.util.List;
import jpabook.jpashop.member.Address;
import jpabook.jpashop.order.Order;
import jpabook.jpashop.order.OrderItem;
import jpabook.jpashop.order.OrderRepository;
import jpabook.jpashop.order.OrderSearch;
import jpabook.jpashop.order.OrderStatus;
import lombok.Getter;
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

  @GetMapping("/api/v2/orders")
  public List<OrderDto> ordersV2() {
    List<Order> orders = orderRepository.findAllByString(new OrderSearch());
    List<OrderDto> result = orders.stream()
        .map(OrderDto::new)
        .collect(toList());

    return result;
  }

  @GetMapping("/api/v3/orders")
  public List<OrderDto> ordersV3() {
    List<Order> orders = orderRepository.findAllWithItem();
    List<OrderDto> result = orders.stream()
        .map(OrderDto::new)
        .collect(toList());

    return result;
  }

  @Getter
  static class OrderDto {

    private Long orderId;
    private String name;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private Address address;
    private List<OrderItemDto> orderItems;

    public OrderDto(Order order) {
      orderId = order.getId();
      name = order.getMember().getName();
      orderDate = order.getOrderDate();
      orderStatus = order.getStatus();
      address = order.getDelivery().getAddress();
      orderItems = order.getOrderItems().stream()
          .map(OrderItemDto::new)
          .collect(toList());
    }
  }

  @Getter
  static class OrderItemDto {

    private String itemName; // 상품 명
    private int orderPrice; // 주문 가격
    private int count; // 주문 수량

    public OrderItemDto(OrderItem orderItem) {
      itemName = orderItem.getItem().getName();
      orderPrice = orderItem.getOrderPrice();
      count = orderItem.getCount();
    }
  }
}
