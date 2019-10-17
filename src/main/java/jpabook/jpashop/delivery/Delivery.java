package jpabook.jpashop.delivery;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import jpabook.jpashop.member.Address;
import jpabook.jpashop.order.Order;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Delivery {

  @Id
  @GeneratedValue
  @Column(name = "delivery_id")
  private Long id;

  @OneToOne(mappedBy = "delivery")
  private Order order;

  @Embedded
  private Address address;

  @Enumerated(EnumType.STRING)
  private DeliveryStatus status;
}
