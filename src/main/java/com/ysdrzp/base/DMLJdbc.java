package com.ysdrzp.base;

import com.ysdrzp.util.JdbcUtil;
import org.junit.Test;
import java.sql.Connection;
import java.sql.Statement;

public class DMLJdbc {

    /**
     * 增加
     */
    @Test
    public void testInsert(){
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = JdbcUtil.getConnection();
            // 创建Statement对象
            stmt = conn.createStatement();
            // 创建sql语句
            String sql = "INSERT INTO user(name,password) VALUES('admin','123456')";
            // 执行sql
            int count = stmt.executeUpdate(sql);
            System.out.println("影响了"+count+"行");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally{
            JdbcUtil.close(conn, stmt);
        }
    }

    /**
    *删除
    */
    @Test
    public void testDelete(){
        Connection conn = null;
        Statement stmt = null;
        int id = 1;
        try {
            conn = JdbcUtil.getConnection();
            // 创建Statement对象
            stmt = conn.createStatement();
            // 创建sql语句
            String sql = "DELETE FROM user WHERE id="+id+"";
            System.out.println(sql);
            // 执行sql
            int count = stmt.executeUpdate(sql);
            System.out.println("影响了"+count+"行");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally{
            JdbcUtil.close(conn, stmt);
        }
    }

    /**
    * 修改
    */
    @Test
    public void testUpdate(){
        Connection conn = null;
        Statement stmt = null;
        String name = "test";
        int id = 1;
        try {
            conn = JdbcUtil.getConnection();
            // 创建Statement对象
            stmt = conn.createStatement();
            // 创建sql语句
            String sql = "UPDATE user SET name='"+name+"' WHERE id="+id+"";
            System.out.println(sql);
            // 执行sql
            int count = stmt.executeUpdate(sql);
            System.out.println("影响了"+count+"行");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally{
            //关闭资源
            JdbcUtil.close(conn, stmt);
        }
    }

}
