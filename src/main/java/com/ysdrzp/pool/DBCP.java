package com.ysdrzp.pool;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbcp2.BasicDataSourceFactory;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

public class DBCP {

    @Test
    public void testDbcp() throws Exception {
        // DBCP连接池核心类
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setUrl("jdbc:mysql://localhost:3306/mysql-learn?serverTimezone=UTC&useUnicode=true&characterEncoding=utf8");
        basicDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        basicDataSource.setUsername("root");
        basicDataSource.setPassword("root");
        basicDataSource.setInitialSize(2);
        basicDataSource.setMaxTotal(8);
        basicDataSource.setMaxIdle(4);
        basicDataSource.setMinIdle(2);

        Connection connection = basicDataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("delete from user where id=4");
        preparedStatement.executeUpdate();
        release(connection, preparedStatement);
    }

    @Test
    public void testProp() throws Exception {
        Properties properties = new Properties();
        InputStream inputStream = DBCP.class.getResourceAsStream("/dbcp.properties");
        properties.load(inputStream);
        DataSource dataSource = BasicDataSourceFactory.createDataSource(properties);

        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("delete from user where id=4");
        preparedStatement.executeUpdate();
        release(connection, preparedStatement);
    }

    /**
     * 释放连接
     * @param conn
     * @param st
     */
    public static void release(Connection conn, Statement st){
        if (st!=null){
            try {
                st.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        if (conn!=null){
            try {
                //将Connection连接对象还给数据库连接池
                conn.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    /**
     * 释放连接
     * @param conn
     * @param st
     * @param rs
     */
    public static void release(Connection conn, Statement st, ResultSet rs){
        if (rs!=null){
            try {
                rs.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        if (st!=null){
            try {
                st.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        if (conn!=null){
            try {
                //将Connection连接对象还给数据库连接池
                conn.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
