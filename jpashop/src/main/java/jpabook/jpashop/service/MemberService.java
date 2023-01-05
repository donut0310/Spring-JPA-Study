package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) // 조회 작업의 경우 readonly = true, 쓰기의 경우 readonly = false
/**
 * [방법 2] @AllArgsConstructor -> 생성자를 만들어줌
 * @AllArgsConstructor => public MemberService(MemberRepository memberRepository) {
 *        this.memberRepository = memberRepository}
 */
//========//

/**
 * [방법 3] @RequiredArgsConstructor -> final 키워드를 가지고 있는 필드만 생성자를 만들어 준다.
 */
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository; // 더 이상 변경될 일이 없기 때문에 final 선언

    /**
     * [방법 1] 생성자 주입 사용, @Autowired 생략 가능
     * @Autowired
     *     public MemberService(MemberRepository memberRepository) {
     *         this.memberRepository = memberRepository
     *     }
     */

    /**
     * 회원 가입
     */
    @Transactional
    public Long join(Member member){
        validateDuplicateMember(member); // 중복 회원 검증
        memberRepository.save((member));
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    // 회원 전체 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }
}


