package hello.core.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MemberServiceImpl implements MemberService{

    //Appconfig에서는 의존관계를 수동으로 주입했는데 component에서는 자동으로 해주니까
    //의존관계 주입을 autowired로 해줌
    //memberRepository 를 찾아와서 주입을 해줌

    //1. private final MemberRepository memberRepository = new MemoryMemberRepository(); //구체에 의존
    private final MemberRepository memberRepository;

    /**
     * 아래 코드는 AppConfig에서 생성 후 주입해주기 때문에 만들음.
     * MemberRepository 만 의존하고 상세 구현체는 의존하지 않음
     * 어떤 객체가 들어올지 알 수 없음
     * @param memberRepository
     */
    @Autowired
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }

    //test용도
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
