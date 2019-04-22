package vip.wen.spring.orm.Dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import vip.wen.spring.orm.demo.Member;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MemberDao {

    JdbcTemplate jdbcTemplate;

    @Resource(name="dataSource")
    public void setDataSource(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Member> selectAll(){
        final List<Member> result = new ArrayList<>();
        String sql = "select * from t_member";
        jdbcTemplate.query(sql, new RowMapper<Object>() {
            @Override
            public Object mapRow(ResultSet rs, int i) throws SQLException {
                Member m = new Member();
                rs.getLong(i);
                result.add(m);
                return m;
            }
        });
        return result;
    }
}
