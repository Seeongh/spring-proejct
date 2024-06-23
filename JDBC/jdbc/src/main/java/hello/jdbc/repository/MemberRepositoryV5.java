package hello.jdbc.repository;

import hello.jdbc.domain.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * jdbc 템플릿 사용
 *
 * - 이 반복들을 추후 템플릿 패턴으로 해결함 *
 * 트랜잭션, 예외 변환기까지 자동 적용되어있음.
 * 변하는 매퍼, sql등만 변경해줌
 */

@Slf4j
public class MemberRepositoryV5 implements MemberRepository{

    private final JdbcTemplate jdbcTemplate;

    public MemberRepositoryV5(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Member save(Member member) {
        String sql = "insert into member(member_id, money) values (?,?)";
        int update = jdbcTemplate.update(sql, member.getMemberId(), member.getMoney());

        return member;

    }

    @Override
    public Member findById(String memberId) {

        String sql = "select * from member where member_id = ?";
        jdbcTemplate.queryForObject(sql, memberRowMapper(), memberId); )
    }

    //이 결과가 반환이 됨
    private RowMapper<Member> memberRowMapper() {
        return (rs, rowNum) -> {
            Member member = new Member();
            member.setMemberId(rs.getString("member_id"));
            member.setMemberId(String.valueOf(rs.getInt("money")));
            return member;
        }
    }

    @Override
    public void update(String memberId, int money) {

    }

    @Override
    public void delete(String memberId) {

    }
}
