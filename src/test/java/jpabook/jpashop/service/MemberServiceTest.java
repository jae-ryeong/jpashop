package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepositoryOld;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)  // 스프링과 같이 실행한다는 의미
@SpringBootTest // 스프링 부트를 띄운 상태로 실행
@Transactional  // 테스트가 끝나면 롤백해준다.
class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired
    MemberRepositoryOld memberRepositoryOld;

    @Test
    public void 회원가입() throws Exception{
        //given
        Member member = new Member();
        member.setName("kim");

        //when
        Long savedId = memberService.join(member);

        //then
        assertEquals(member, memberRepositoryOld.findOne(savedId));
    }

    @Test()
    public void 중복_회원_예외() throws Exception{
        assertThrows(IllegalStateException.class, () -> {
            // given
            Member member1 = new Member();
            member1.setName("kim1");

            Member member2 = new Member();
            member2.setName("kim1");

            // when
            memberService.join(member1);
            memberService.join(member2);


            // then
            fail("예외가 발생해야 한다.");
        });
    }

}