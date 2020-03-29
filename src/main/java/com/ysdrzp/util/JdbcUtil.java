package com.ysdrzp.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JdbcUtil {

    private static String url = null;
    private static String user = null;
    private static String password = null;
    private static String driverClass = null;

    static{
        try {
            Properties props = new Properties();
            InputStream in = JdbcUtil.class.getResourceAsStream("/jdbc.properties");
            props.load(in);
            url = props.getProperty("url");
            user = props.getProperty("user");
            password = props.getProperty("password");
            driverClass = props.getProperty("driverClass");

            //注册驱动程序
            Class.forName(driverClass);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("驱程程序注册出错");
        }
    }

    /**
    * 获取连接对象
    */
    public static Connection getConnection(){
        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            return conn;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
    * 释放资源
    */
    public static void close(Connection conn, Statement stmt){
        if(stmt!=null){
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        if(conn!=null){
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }

    /**
    * 释放资源
    */
    public static void close(Connection conn, Statement stmt, ResultSet rs){
        if(rs!=null)
            try {
              rs.close();
            } catch (SQLException e1) {
              e1.printStackTrace();
              throw new RuntimeException(e1);
            }
        if(stmt!=null){
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        if(conn!=null){
             try {
                 conn.close();
             } catch (SQLException e) {
                 e.printStackTrace();
                 throw new RuntimeException(e);
             }
        }
    }

    public static void main(String[] args) {
        getConnection();
    }

}
