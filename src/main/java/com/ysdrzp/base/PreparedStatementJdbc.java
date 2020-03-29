package com.ysdrzp.base;

import com.ysdrzp.util.JdbcUtil;
import org.junit.Test;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PreparedStatementJdbc {

    /**
     * 增加
     */
    @Test
    public void testInsert() {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = JdbcUtil.getConnection();
            // 准备预编译的sql
            String sql = "INSERT INTO user(name,password) VALUES(?,?)";
            // 执行预编译sql语句(检查语法)
            stmt = conn.prepareStatement(sql);
            // 设置参数值
            stmt.setString(1, "user");
            stmt.setString(2, "1230");
            // 发送参数，执行sql
            int count = stmt.executeUpdate();
            System.out.println("影响了"+count+"行");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            JdbcUtil.close(conn, stmt);
        }
    }

    /**
    * 修改
    */
    @Test
    public void testUpdate() {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = JdbcUtil.getConnection();
            // 准备预编译的sql
            String sql = "UPDATE user SET name=? WHERE id=?";
            // 预编译sql语句(检查语法)
            stmt = conn.prepareStatement(sql);
            // 设置参数值
            stmt.setString(1, "test");
            stmt.setInt(2, 3);
            // 发送参数，执行sql
            int count = stmt.executeUpdate();
            System.out.println("影响了"+count+"行");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            JdbcUtil.close(conn, stmt);
        }
    }

    /**
    * 删除
    */
    @Test
    public void testDelete() {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = JdbcUtil.getConnection();
            // 准备预编译的sql
            String sql = "DELETE FROM user WHERE id=?";
            // 预编译sql语句(检查语法)
            stmt = conn.prepareStatement(sql);
            // 设置参数值
            stmt.setInt(1, 9);
            // 发送参数，执行sql
            int count = stmt.executeUpdate();
            System.out.println("影响了"+count+"行");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            JdbcUtil.close(conn, stmt);
        }
    }

    /**
    * 查询
    */
    @Test
    public void testQuery() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = JdbcUtil.getConnection();
            // 准备预编译的sql
            String sql = "SELECT * FROM user";
            // 预编译
            stmt = conn.prepareStatement(sql);
            // 执行sql
            rs = stmt.executeQuery();
            //5.遍历rs
            while(rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String password = rs.getString("password");
                System.out.println(id+","+name+","+password);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            //关闭资源
            JdbcUtil.close(conn,stmt,rs);
        }
    }
}
