package jpabook.jpashop.member;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

  List<Member> findByName(String name);
}
