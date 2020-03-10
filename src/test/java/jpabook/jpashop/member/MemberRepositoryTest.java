package jpabook.jpashop.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class MemberRepositoryTest {

  @Autowired
  MemberRepositoryOld memberRepository;

  @Test
  @Transactional
  @Rollback(false)
  void testMember() {
    // given
    Member member = new Member();
    member.setName("memberA");

    // when
    memberRepository.save(member);
    Member findMember = memberRepository.findOne(member.getId());

    // then
    Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
    Assertions.assertThat(findMember.getName()).isEqualTo(member.getName());
    Assertions.assertThat(findMember).isEqualTo(member);
  }
}