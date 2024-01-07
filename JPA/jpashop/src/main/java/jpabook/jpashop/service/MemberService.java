package jpabook.jpashop.service;


import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@Transactional(readOnly=true) //성능 최적화  //transaction안에서 jpa는 실행되어야함 - public은 transaction이 걸림
//@AllArgsConstructor //필드 주입되는걸 보고 생성자를 자동으로 만들어줌
@RequiredArgsConstructor //final필드만 가지고 생성자를 만들어줌
public class MemberService {

//    @Autowired //field injection
//    private MemberRepository memberRepository;
    //test시 이걸 바꿀 수 있는 방법이 없음

    private final MemberRepository memberRepository; //값의 변경이 없기 위해 final로 선언

//    @Autowired //setter injection - test시 몫을 직접 주입할 수 있음 하지만 runtime에 변경될 수있음
//    public void setMemberRepository(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }

//    @Autowired //생성자 injection
//    public MemberService(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }

//    //요즘은 autowired없어도 생성자가 1개이면 스프링이 넣어줌
//    public MemberService(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }

    /**
     * 회원 가입
     * @param member
     * @return
     */
    @Transactional(readOnly = false) //읽기전용으로 하면 데이터 변경이 안됨.
    public Long join(Member member) {
        validateDuplicateMember(member); //중복 확인
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        //중복회원이면 예외 exception
        List<Member> findMembers = memberRepository.findByName(member.getName());//같은 name이있는지 찾아봄
        //was가 동시에 여러개 뜨면 타이밍으로 동시에 들어가질 수 있음
        //한번더 방어 처리해줘야함
        //database에 member name을  unique로 잡는걸 권장
        if(!findMembers.isEmpty()){
            log.info("ash");
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    //회원 전체 조회
    @Transactional
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    //회원 한건 조회
    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }
}
