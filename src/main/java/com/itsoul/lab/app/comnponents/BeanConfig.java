package com.itsoul.lab.app.comnponents;

import com.it.soul.lab.sql.SQLExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
    private SQLExecutorFactory creator;

    @Bean("JDBConnectionPool")
    SQLExecutor executor() {
        SQLExecutor exe = creator.executor("testDB");
        System.out.println("Created DB Connections.");
        return exe;
    }

}
