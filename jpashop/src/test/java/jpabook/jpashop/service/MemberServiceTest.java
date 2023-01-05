package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest // 스프링 부트를 띄운 상태에서 테스트하려면 필요함, 없으면 아래 @Autowired 먹통
@Transactional
public class MemberServiceTest {
    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
//    @Rollback(false)
    // 스프링의 Transactional은 기본적으로 롤백이 진행된다. 따라서 @Rollback에 false값을 지정해서 커밋 진행
    public void 회원가입() throws Exception{
        //given
        Member member = new Member();
        member.setName("kim");

        //when
        Long savedId = memberService.join(member);

        //then
        /**
         * JPA에서 같은 트랜잭션 내에서 같은 엔티티(pk 값이 같다면) 같은 영속성 컨텍스트에서 똑같이 관리된다.
         * 즉, 한 개만 존재한다.
         */
        assertEquals(member, memberRepository.findOne(savedId));
    }

    // expected = IllegalStateException.class => 아래 try-catch문 생략 가능
    @Test(expected = IllegalStateException.class)
    public void 중복_회원_예외() throws Exception {
        //given
        Member member1 = new Member();
        member1.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");

        //when
        memberService.join(member1);
        memberService.join(member2);
//        memberService.join(member1);
//        try {
//            memberService.join(member2);
//        } catch (IllegalStateException e) {
//            return;
//        }

        //then
        fail("예외 발생");
    }
}