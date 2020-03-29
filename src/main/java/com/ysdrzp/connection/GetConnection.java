package com.ysdrzp.connection;

import com.mysql.cj.jdbc.Driver;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class GetConnection {

    private String url = "jdbc:mysql://localhost:3306/mysql-learn?serverTimezone=UTC&useUnicode=true&characterEncoding=utf8";
    private String user = "root";
    private String password = "root";

    /**
     * 第一种方式
     * @throws Exception
     */
    @Test
    public void test1() throws Exception{
        // 1、创建驱动程序类对象
        Driver driver = new Driver();
        Properties props = new Properties();
        props.setProperty("user", user);
        props.setProperty("password", password);
        // 2、连接数据库，返回连接对象
        Connection conn = driver.connect(url, props);
        System.out.println(conn);
    }

    /**
     * 第二种方式
     * @throws Exception
     */
    @Test
    public void test2() throws Exception{

         // 创建驱动程序类对象
         Driver driver = new Driver();
         // 1、注册驱动程序
         DriverManager.registerDriver(driver);
         // 2.连接到具体的数据库
         Connection conn = DriverManager.getConnection(url, user, password);
         System.out.println(conn);
    }

     /**
     * 第三种方式（推荐）
     * @throws Exception
     */
    @Test
    public void test3() throws Exception{

        // 1、通过得到字节码对象的方式加载静态代码块，从而注册驱动程序
        Class.forName("com.mysql.cj.jdbc.Driver");
        // 2、连接到具体的数据库
        Connection conn = DriverManager.getConnection(url, user, password);
        System.out.println(conn);
    }

}
