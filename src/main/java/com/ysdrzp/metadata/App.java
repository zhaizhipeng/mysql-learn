package com.ysdrzp.metadata;

import com.ysdrzp.util.JdbcUtil;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

public class App {

    /**
     * 1. 数据库元数据
     * @throws Exception
     */
    @Test
    public void testDB() throws Exception {
        Connection connection = JdbcUtil.getConnection();
        // 获取数据库元数据
        DatabaseMetaData databaseMetaData = connection.getMetaData();
        System.out.println(databaseMetaData.getUserName());
        System.out.println(databaseMetaData.getURL());
        System.out.println(databaseMetaData.getDatabaseProductName());
    }

    /**
     * 2. 参数元数据
     * @throws Exception
     */
    @Test
    public void testParams() throws Exception {
        Connection connection = JdbcUtil.getConnection();
        String sql = "select * from account where account_name=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, "user");
        // 参数元数据
        ParameterMetaData parameterMetaData = preparedStatement.getParameterMetaData();
        // 获取参数的个数
        int count = parameterMetaData.getParameterCount();
        System.out.println(count);
    }

    /**
     * 3. 结果集元数据
     * @throws Exception
     */
    @Test
    public void testRs() throws Exception {
        String sql = "select * from account ";
        Connection connection = JdbcUtil.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        // 得到结果集元数据(目标：通过结果集元数据，得到列的名称)
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        while (resultSet.next()) {
            // 获取列的个数
            int count = resultSetMetaData.getColumnCount();
            // 遍历，获取每一列的列的名称
            for (int i=0; i<count; i++) {
                // 得到列的名称
                String columnName = resultSetMetaData.getColumnName(i + 1);
                // 获取每一行的每一列的值
                Object columnValue = resultSet.getObject(columnName);
                System.out.print(columnName + "=" + columnValue + ",");
            }
        }
    }

}
