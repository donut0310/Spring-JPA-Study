package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    /** @PersistenceContext
     * 원래 EntityManager는 @AutoWired가 아닌 @PersistenceContext로 인젝션이 가능하지만,
     * 스프링 부트가 @AutoWired도 지원을 해주기 때문에 가능하다.
     * 스프링 데이터 JPA를 사용하면 @AutoWired로 변경 가능
     * 이후, 필드에 final 키워드를 추가한 뒤 @RequiredArgsConstructor를 선언하면 된다.
     */
    private final EntityManager em;

    public void save(Member member) {
        em.persist(member);
    }

    public Member findOne(Long id){
        return em.find(Member.class, id);
    }

    public List<Member> findAll(){
        // JPQL => SQL과 달리 테이블이 아닌 엔티티 객체를 대상으로 쿼리를 실행
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public List<Member> findByName(String name){
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }
}
