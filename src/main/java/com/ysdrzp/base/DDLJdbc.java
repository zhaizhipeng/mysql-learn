package com.ysdrzp.base;

import com.ysdrzp.util.JdbcUtil;
import org.junit.Test;
import java.sql.Connection;
import java.sql.Statement;

public class DDLJdbc {

    /**
     * 执行DDL语句(创建表)
     */
    @Test
    public void testCreate(){
        Statement stmt = null;
        Connection conn = null;
        try {
            // 获取连接对象
            conn = JdbcUtil.getConnection();
            // 创建Statement
            stmt = conn.createStatement();
            // 准备sql
            String sql = "CREATE TABLE user(id INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(20), password VARCHAR(2))";
            // 发送sql语句，执行sql语句,得到返回结果
            int count = stmt.executeUpdate(sql);
            System.out.println("影响了"+count+"行！");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally{
            JdbcUtil.close(conn,stmt);
        }
    }

}
