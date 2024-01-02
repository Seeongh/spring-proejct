package hello.core.member;

/**
 * 회원 가입, 조회
 */
public interface MemberService {

    void join(Member member);

    Member findMember(Long memberId);
}
