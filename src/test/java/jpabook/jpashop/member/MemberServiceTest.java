package jpabook.jpashop.member;

import static org.junit.jupiter.api.Assertions.*;

import javax.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class MemberServiceTest {

  @Autowired
  MemberService memberService;

  @Autowired
  MemberRepository memberRepository;

  @Autowired
  EntityManager em;

  @Test
  // @Rollback(false)
  void memberJoin() {
    // given
    Member member = new Member();
    member.setName("lee");

    // when
    Long savedId = memberService.join(member);
    // em.flush();

    // then
    assertEquals(member, memberRepository.findOne(savedId));
  }

  @Test()
  void duplicateMemberException() {
    // given
    Member member1 = new Member();
    member1.setName("lee");

    Member member2 = new Member();
    member2.setName("lee");

    assertThrows(IllegalStateException.class, () -> {
      memberService.join(member1);
      memberService.join(member2);
    });

    // when
    // memberService.join(member1);
    // memberService.join(member2);

    // then
    // fail("예외가 발생해야 한다.");
  }
}