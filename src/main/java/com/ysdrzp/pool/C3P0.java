package com.ysdrzp.pool;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.ysdrzp.util.JdbcUtil;
import org.junit.Test;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class C3P0 {

    @Test
    public void testCode() throws Exception {
        // 创建连接池核心工具类
        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
        // 设置连接参数：url、驱动、用户密码、初始连接数、最大连接数
        comboPooledDataSource.setJdbcUrl("jdbc:mysql://localhost:3306/mysql-learn?serverTimezone=UTC&useUnicode=true&characterEncoding=utf8");
        comboPooledDataSource.setDriverClass("com.mysql.cj.jdbc.Driver");
        comboPooledDataSource.setUser("root");
        comboPooledDataSource.setPassword("root");
        comboPooledDataSource.setInitialPoolSize(2);
        comboPooledDataSource.setMaxPoolSize(8);
        comboPooledDataSource.setMaxIdleTime(1000);

        Connection connection = comboPooledDataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("delete from user where id=7");
        preparedStatement.executeUpdate();
        JdbcUtil.close(connection,preparedStatement);
    }

    @Test
    public void testXML() throws Exception {
        // 自动加载src下c3p0的配置文件【c3p0-config.xml】
        //ComboPooledDataSource dataSource = new ComboPooledDataSource();
        ComboPooledDataSource dataSource = new ComboPooledDataSource("dataSource");
        PreparedStatement preparedStatement = null;

        Connection connection = dataSource.getConnection();
        for (int i=1; i<11;i++){
            String sql = "insert into user(name,password) values(?,?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "Rose" + i);
            preparedStatement.setInt(2, 1);
            preparedStatement.executeUpdate();
        }
        preparedStatement.close();
        JdbcUtil.close(connection, preparedStatement);
    }

}
