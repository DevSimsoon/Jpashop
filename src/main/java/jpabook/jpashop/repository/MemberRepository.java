package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository // 저장소
public class MemberRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(Member member) { // 저장
        em.persist(member);
    }

    public Member findOne(Long id) { // 하나찾기
        return em.find(Member.class, id);
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public List<Member> findByName(String name) {
        return  em.createQuery("select m from Member m where m.name = :name",
                Member.class)
                .setParameter("name", name)
                .getResultList();
    }

    // @Repository : 스프링 빈으로 등록, JPA 예외 스프링 기반 예외로 변환
    // @PersistenceContext : 엔티티 매니저 주입
    // @PersistenceUnit : 엔티티 매니저 팩토리를 주입

    // 다음에 만들어야 하는 것
    // 회원 서비스개발
    // 상품 도메인 개발
    // 상품 엔티티 개발 (비즈니스 로직 추가)
    // 상품 레포지토리 개발
    // 주문 도메인 개발

}
