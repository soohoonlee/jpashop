package jpabook.jpashop.order.query;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderQueryRepository {

  private final EntityManager em;

  public List<OrderQueryDto> findOrderQueryDtos() {
    List<OrderQueryDto> result = findOrders();
    result.forEach(orderQueryDto -> {
      List<OrderItemQueryDto> orderItems = findOrderItems(orderQueryDto.getOrderId());
      orderQueryDto.setOrderItems(orderItems);
    });
    return result;
  }

  public List<OrderQueryDto> findAllByDtoOptimization() {
    List<OrderQueryDto> result = findOrders();

    Map<Long, List<OrderItemQueryDto>> orderItemMap = findOrderItemMap(toOrderIds(result));

    result.forEach(orderQueryDto -> orderQueryDto.setOrderItems(orderItemMap.get(orderQueryDto.getOrderId())));

    return result;
  }

  private Map<Long, List<OrderItemQueryDto>> findOrderItemMap(List<Long> orderIds) {
    List<OrderItemQueryDto> orderItems = em.createQuery(
        "select new jpabook.jpashop.order.query.OrderItemQueryDto(oi.order.id, i.name, oi.orderPrice, oi.count)"
            + " from OrderItem oi"
            + " join oi.item i"
            + " where oi.order.id in :orderIds", OrderItemQueryDto.class)
        .setParameter("orderIds", orderIds)
        .getResultList();

    return orderItems.stream()
        .collect(groupingBy(OrderItemQueryDto::getOrderId));
  }

  private List<Long> toOrderIds(List<OrderQueryDto> result) {
    return result.stream()
          .map(OrderQueryDto::getOrderId)
          .collect(toList());
  }

  private List<OrderItemQueryDto> findOrderItems(Long orderId) {
    return em.createQuery(
        "select new jpabook.jpashop.order.query.OrderItemQueryDto(oi.order.id, i.name, oi.orderPrice, oi.count)"
            + " from OrderItem oi"
            + " join oi.item i"
            + " where oi.order.id = :orderId", OrderItemQueryDto.class)
        .setParameter("orderId", orderId)
        .getResultList();
  }

  private List<OrderQueryDto> findOrders() {
    return em.createQuery(
        "select new jpabook.jpashop.order.query.OrderQueryDto(o.id, m.name, o.orderDate, o.status, d.address)"
            + " from Order o"
            + " join o.member m"
            + " join o.delivery d", OrderQueryDto.class)
        .getResultList();
  }
}
