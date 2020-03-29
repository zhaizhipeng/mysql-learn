package com.ysdrzp.base;

import com.ysdrzp.util.JdbcUtil;
import org.junit.Test;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class DQLJdbc {

    @Test
    public void testSelect() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = JdbcUtil.getConnection();
            stmt = conn.createStatement();
            // 准备sql
            String sql = "SELECT * FROM user";
            // 执行sql
            rs = stmt.executeQuery(sql);
            //遍历结果
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String password = rs.getString("password");
                System.out.println(id + "," + name + "," + password);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            JdbcUtil.close(conn, stmt, rs);
        }
    }
}
