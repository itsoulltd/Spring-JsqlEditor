package com.itsoul.lab.app.comnponents;

import com.it.soul.lab.connect.JDBConnectionPool;
import com.it.soul.lab.sql.SQLExecutor;
import com.it.soul.lab.sql.query.models.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jndi.JndiTemplate;

import javax.naming.NamingException;
//import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.apache.tomcat.jdbc.pool.DataSource;

@Configuration
public class BeanConfig {

    @Bean("HelloBean")
    public String getHello(){
        return "Hi Spring Hello";
    }

//    @Bean("mysqlDataSource")
//    public DataSource mysqlDataSource() {
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
//        dataSource.setUrl("jdbc:mysql://localhost:3306/testDB");
//        dataSource.setUsername("root");
//        dataSource.setPassword("towhid@123");
//
//        try {
//            testing(dataSource.getConnection());
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return dataSource;
//    }

//    @Bean("mysqlDataSource")
//    DataSource mysqlDataSource() {
//        DataSource dataSource = null;
//        JndiTemplate jndi = new JndiTemplate();
//        try {
//            dataSource = jndi.lookup("java:comp/env/jdbc/testDB", DataSource.class);
//            //testing(null);
//        } catch (NamingException e) {
//            //logger.error("NamingException for java:comp/env/jdbc/yourname", e);
//            System.out.println(e.getMessage());
//        }
//        return dataSource;
//    }

//    @Bean("JndiTemplate")
//    SQLExecutor sqlExecutor() {
//        SQLExecutor exe = null;
//        try {
//            JndiTemplate jndi = new JndiTemplate();
//            DataSource dataSource = jndi.lookup("java:comp/env/jdbc/testDB", DataSource.class);
//            exe = new SQLExecutor(dataSource.getConnection());
//        } catch (NamingException | SQLException e) {
//            System.out.println(e.getMessage());
//        }
//        return exe;
//    }

    @Autowired
    private Environment env;

    private DataSource backupDataSource(){

        String url = String.format("jdbc:mysql://%s:3306/testDB", env.getProperty("DATABASE_URL"));
        System.out.println("Database URL: " + url);

        DataSource ds = new DataSource();
        ds.setDriverClassName("com.mysql.jdbc.Driver");
        ds.setUrl(url);
        ds.setUsername("root");
        ds.setPassword("towhid@123");
        ds.setInitialSize(5);
        ds.setMaxActive(10);
        ds.setMaxIdle(5);
        ds.setMinIdle(2);

        return ds;
    }

    @Bean("JDBConnectionPool")
    SQLExecutor executor() {
        SQLExecutor exe = null;
        try {

            //JDBConnectionPool.configure("java:comp/env/jdbc/testDB");
            //exe = new SQLExecutor(JDBConnectionPool.connection());

            exe = new SQLExecutor(backupDataSource().getConnection());

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Created DB Connections.");
        return exe;
    }

}
