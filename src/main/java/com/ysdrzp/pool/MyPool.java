package com.ysdrzp.pool;

import com.ysdrzp.util.JdbcUtil;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 * 自定义连接池
 */
public class MyPool {

    // 初始化连接数目
    private static int init_count = 3;
    // 最大连接数
    private static int max_count = 6;
    // 记录当前使用连接数
    private static int current_count = 0;
    // 连接池
    private static LinkedList<Connection> pool = new LinkedList<Connection>();

    static {
        // 初始化连接
        for (int i=0; i<init_count; i++){
            // 记录当前连接数目
            current_count++;
            // 创建原始的连接对象
            Connection connection = createConnection();
            // 把连接加入连接池
            pool.addLast(connection);
        }
    }

    /**
     * 创建连接
     * @return
     */
    private static Connection createConnection(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            // 原始的目标对象
            final Connection connection = JdbcUtil.getConnection();

            // 对连接对象创建代理对象
            Connection proxy = (Connection) Proxy.newProxyInstance(
                    connection.getClass().getClassLoader(),
                    new Class[]{Connection.class},
                    new InvocationHandler() {
                        @Override
                        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                            // 方法返回值
                            Object result = null;
                            // 当前执行的方法的方法名
                            String methodName = method.getName();
                            if ("close".equals(methodName)) {
                                System.out.println("begin:当前执行close方法开始！");
                                realeaseConnection(connection);
                                System.out.println("end: 当前连接已经放入连接池了！");
                            } else {
                                result = method.invoke(connection, args);
                            }
                            return result;
                    }
            });
            return proxy;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 从连接池中获取连接
     * @return
     */
    public Connection getConnection(){
        if (pool.size() > 0){
            return pool.removeFirst();
        }
        if (current_count < max_count) {
            current_count++;
            return createConnection();
        }
        throw new RuntimeException("当前连接已经达到最大连接数目");
    }

    /**
     * 释放连接
     * @param connection
     */
    public static void realeaseConnection(Connection connection) {
        if (pool.size() < init_count){
            pool.addLast(connection);
        } else {
            try {
                current_count--;
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String[] args) throws SQLException {
        MyPool pool = new MyPool();
        System.out.println("当前连接: " + pool.current_count);
        System.out.println("连接池: " + pool.pool.size());

        pool.getConnection();
        pool.getConnection();
        Connection connection4 = pool.getConnection();
        Connection connection3 = pool.getConnection();
        Connection connection2 = pool.getConnection();
        Connection connection1 = pool.getConnection();

        System.out.println("当前连接: " + pool.current_count);
        System.out.println("连接池: " + pool.pool.size());
        connection1.close();
        connection2.close();
        connection3.close();
        connection4.close();
        System.out.println("连接池：" + pool.pool.size());
        System.out.println("当前连接: " + pool.current_count);
    }

}
