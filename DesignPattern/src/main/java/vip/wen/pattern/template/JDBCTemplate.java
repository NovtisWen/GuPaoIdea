package vip.wen.pattern.template;



import javax.sql.DataSource;
import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JDBCTemplate {

    private DataSource dataSource;

    public JDBCTemplate(DataSource dataSource){
        this.dataSource = dataSource;
    }


    public List<?> executeQuery(String sql,RowMapper<?> rowMapper,Object[] values) throws Exception{

        //1、获得Connection
        Connection conn = this.dataSource.getConnection();
        //2、创建语句集
        PreparedStatement ps = createPreparedStatement(conn,sql);

        //3、执行语句集
        ResultSet rs = executeQuery(ps,values);
        //4、生成结果集
        List<?> list = prepareResultSet(rs,rowMapper);
        //5、关闭结果集
        closeResultSet(rs);
        //6、关闭连接
        closeConnection(conn);
        return list;
    }

    private void closeConnection(Connection conn) throws SQLException {
        conn.close();
    }

    private void closeResultSet(ResultSet rs) throws SQLException {
        rs.close();
    }


    private List<?> prepareResultSet(ResultSet rs,RowMapper<?> rowMapper) throws SQLException {
        List<Object> list = new ArrayList<Object>();
        int row = 1;
        while (rs.next()){
            list.add(rowMapper.mapRow(rs,row++));
        }
        return list;
    }

    private ResultSet executeQuery(PreparedStatement ps, Object[] values) throws SQLException {
        ResultSet rs = null;
        for (int i=0;i<values.length;i++) {
            ps.setObject(i,values[i]);
        }
        rs = ps.executeQuery();
        return rs;
    }

    private PreparedStatement createPreparedStatement(Connection conn, String sql) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(sql);
        return ps;
    }

}
