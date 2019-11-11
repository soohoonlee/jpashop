package jpabook.jpashop.item;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateItemDto {

  private Long id;

  private String name;
  private int price;
  private int stockQuantity;
}
