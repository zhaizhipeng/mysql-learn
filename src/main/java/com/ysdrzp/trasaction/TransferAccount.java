package com.ysdrzp.trasaction;

import com.ysdrzp.util.JdbcUtil;
import org.junit.Test;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Savepoint;

/**
 * 转账
 */
public class TransferAccount {

    private Connection connection;
    private PreparedStatement preparedStatement;

    /**
     * 转账，没有使用事务
     */
    @Test
    public void trans() {
        String sql_zs = "UPDATE account SET money=money-1000 WHERE account_name ='张三';";
        String sql_ls = "UPDATE account SET money1=money+1000 WHERE account_name='李四';";
        try {
            connection = JdbcUtil.getConnection();
            // 第一次转账
            preparedStatement = connection.prepareStatement(sql_zs);
            preparedStatement.executeUpdate();
            // 第二次转账
            preparedStatement = connection.prepareStatement(sql_ls);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.close(connection, preparedStatement);
        }
    }

    /**
     * 转账，使用事务
     */
    @Test
    public void trans2() {
        String sql_zs = "UPDATE account SET money=money-1000 WHERE account_name='张三'";
        String sql_ls = "UPDATE account SET money=money+1000 WHERE account_name ='李四'";
        try {
            connection = JdbcUtil.getConnection();
            // 设置事务为手动提交
            connection.setAutoCommit(false);
            /*** 第一次执行SQL ***/
            preparedStatement = connection.prepareStatement(sql_zs);
            preparedStatement.executeUpdate();
            /*** 第二次执行SQL ***/
            preparedStatement = connection.prepareStatement(sql_ls);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
            }
            e.printStackTrace();
        } finally {
            try {
                connection.commit();
                JdbcUtil.close(connection, preparedStatement);
            } catch (SQLException e) {
            }
        }
    }

    /**
     * 转账，使用事务， 回滚到指定的代码段
     */
    @Test
    public void trans3() {
        // 定义标记
        Savepoint sp = null;
        /*** 第一次执行SQL ***/
        String sql_zs1 = "UPDATE account SET money=money-1000 WHERE account_name ='张三';";
        String sql_ls1 = "UPDATE account SET money=money+1000 WHERE account_name ='李四';";
        /*** 第二次执行SQL ***/
        String sql_zs2 = "UPDATE account SET money=money-500 WHERE account_name ='张三';";
        String sql_ls2 = "UPDATE1 account SET money1=money+500 WHERE account_name ='李四';";
        try {
            connection = JdbcUtil.getConnection();
            // 设置事务手动提交
            connection.setAutoCommit(false);
            /*** 第一次转账 ***/
            preparedStatement = connection.prepareStatement(sql_zs1);
            preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement(sql_ls1);
            preparedStatement.executeUpdate();
            // 回滚到这个位置？
            sp = connection.setSavepoint();
            /*** 第二次转账 ***/
            preparedStatement = connection.prepareStatement(sql_zs2);
            preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement(sql_ls2);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            try {
                connection.rollback(sp);
            } catch (SQLException e1) {
            }
            e.printStackTrace();
        } finally {
            try {
                connection.commit();
            } catch (SQLException e) {
            }
            JdbcUtil.close(connection, preparedStatement);
        }
    }

}
