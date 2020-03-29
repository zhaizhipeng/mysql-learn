package com.ysdrzp.base;

import com.ysdrzp.model.User;
import com.ysdrzp.util.JdbcUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BatchInsert {

    private static Connection connection;
    private static PreparedStatement preparedStatement;
    private static ResultSet resultSet;

    public static void save(List<User> list) {
        String sql = "INSERT INTO user (name,password) values(?,?)";
        try {
            connection = JdbcUtil.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            for (int i=0; i<list.size(); i++) {
                User user = list.get(i);
                preparedStatement.setString(1, user.getName());
                preparedStatement.setString(2, user.getPassword());
                preparedStatement.addBatch();
                if (i % 5 == 0) {
                    preparedStatement.executeBatch();
                    preparedStatement.clearBatch();
                }
            }
            preparedStatement.executeBatch();
            preparedStatement.clearBatch();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.close(connection, preparedStatement, resultSet);
        }
    }

    public static void main(String[] args) {
        List<User> list = new ArrayList<User>();
        for (int i=1; i<21; i++) {
            User user = new User();
            user.setName("Jack" + i);
            user.setPassword("888" + i);
            list.add(user);
        }
        save(list);
    }
}
