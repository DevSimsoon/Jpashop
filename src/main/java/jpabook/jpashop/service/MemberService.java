package jpabook.jpashop.service;


import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional (readOnly = true)
// JPA에 모든 데이터변경 로직은 가급적 트랜잭션에서 실행되어야한다.
// 클래스레벨에서 어노테이션을 걸면 모든 퍼블릭 메서드가 트랜잭션이 걸린다.
// 스프링 어노테이션을 이용해야함(javax는 안 됨!)
// readOnly는 조회시 성능 최적화 (조회시에만!)
@RequiredArgsConstructor // final 이 붙은 필드 생성자를 자동생성함
public class MemberService {

    // @Autowired 쓰면 안 됨.
    // ㄴ> 필드 인젝션(생성자 주입)
    private final MemberRepository memberRepository;

    // setter 인젝션 예시 ( 사용 지양 )
    // @AutoWired
    // public void setMemberRepository(MemberRepository memberRepository) {
    //      this.memberRepository = memberRepository;
    // }

    // Autowired 생성자 주입 < 그나마 이게 가장 적절
    // lombok 어노테이션 으로 인해서 생성자 생략
    // public MemberService(MemberRepository memberRepository) {
    //      this.memberRepository = memberRepository;
    // }

    /**
     * 회원가입
     * */
    @Transactional // ReadOnly 가 되면 안되므로 따로 걸음
    public Long join(Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    // 멀티쓰레드 상황을 고려해서 DB에 name을 유니크 제약조건을 거는 것이 좋다.
    // 그렇지 않으면 두 회원이 동일한 이름으로 동시에 가입하는 경우, validate를 통과할 수도 있다.
    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다!");
        }
    }


    /**
     * 회원 전체조회
     * */
    public List<Member> findMembers() { return memberRepository.findAll(); }

    public Member findOne(Long memberId) { return memberRepository.findOne(memberId); }

    @Transactional
    public void update(Long id, String name) { // update는 수정
        Member member = memberRepository.findOne(id);
        member.setName(name); // 변경감지
    }

}
