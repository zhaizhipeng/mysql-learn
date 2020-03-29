package com.ysdrzp.pool;

import com.ysdrzp.util.JdbcUtil;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbcp2.BasicDataSourceFactory;
import org.junit.Test;
import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
        JdbcUtil.close(connection, preparedStatement);
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
        JdbcUtil.close(connection, preparedStatement);
    }

}
