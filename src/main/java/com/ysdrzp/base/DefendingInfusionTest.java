package com.ysdrzp.base;

import com.ysdrzp.util.JdbcUtil;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * 防注入测试
 */
public class DefendingInfusionTest {

    private String name = "userabc' OR 1=1 -- ";
    //private String name = "eric";
    private String password = "123456";

    /**
     * Statment存在sql被注入的风险
     */
    @Test
    public void testByStatement(){
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = JdbcUtil.getConnection();
            stmt = conn.createStatement();
            String sql = "SELECT * FROM user WHERE NAME='"+name+"' AND PASSWORD='"+password+"'";
            rs = stmt.executeQuery(sql);
            if(rs.next()){
                System.out.println("登录成功");
            }else{
                System.out.println("登录失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            JdbcUtil.close(conn, stmt ,rs);
        }
    }

    /**
     * PreparedStatement可以有效地防止sql被注入
     */
    @Test
    public void testByPreparedStatement(){
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = JdbcUtil.getConnection();
            String sql = "SELECT * FROM user WHERE NAME=? AND PASSWORD=?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setString(2, password);
            rs = stmt.executeQuery();
            if(rs.next()){
                System.out.println("登录成功");
            }else{
                System.out.println("登录失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            JdbcUtil.close(conn, stmt ,rs);
        }
    }

}
