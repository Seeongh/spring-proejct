package hello.core.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MemberServiceImpl implements MemberService{

    //Appconfig에서는 의존관계를 수동으로 주입했는데 component에서는 자동으로 해주니까
    //의존관계 주입을 autowired로 해줌
    //memberRepository 를 찾아와서 주입을 해줌

    private final MemberRepository memberRepository;

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
