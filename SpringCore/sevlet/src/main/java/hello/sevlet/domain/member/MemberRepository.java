package hello.sevlet.domain.member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class MemberRepository {

    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;

    private static final MemberRepository instance = new MemberRepository(); //싱글톤 패턴

    public static MemberRepository getInstance() {
        return instance;
    }

    private  MemberRepository() { //private로 생성자 막아주기
    }

    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member ;
    }

    public Member findById(Long id){
        return store.get(id);
    }

    public List<Member> findAll() {
        return new ArrayList<>(store.values()); //store값을 arraylist에 담아 줌
    }
    public void clearStore() {
        store.clear();
    }

}
