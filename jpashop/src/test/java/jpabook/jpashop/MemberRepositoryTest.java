package jpabook.jpashop;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Rollback(false)
public class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;

    @Test
    @Transactional // EntityManager를 통한 모든 데이터 변경은 항상 트랜잭션 안에서 이루어져야 한다.
    public void testMember() throws Exception{
        //given
        Member member = new Member();
        member.setUsername("Kim");

        //when
        Long saveId = memberRepository.save(member);
        Member findmember = memberRepository.find(saveId);

        //then
        Assertions.assertThat(findmember.getId()).isEqualTo(member.getId());
        Assertions.assertThat(findmember.getUsername()).isEqualTo(member.getUsername());
        Assertions.assertThat(findmember).isEqualTo(member);
    }
}