package com.itsoul.lab.app.comnponents;

import com.it.soul.lab.connect.JDBConnectionPool;
import com.it.soul.lab.sql.SQLExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.UUID;

@Component
public class SQLExecutorFactory {

    @Autowired
    private Environment env;

    private DataSource createDataSource(){

        String url = env.getProperty("spring.datasource.url");
        System.out.println("Database URL: " + url);

        org.apache.tomcat.jdbc.pool.DataSource ds = new org.apache.tomcat.jdbc.pool.DataSource();
        ds.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
        ds.setUrl(url);
        ds.setUsername("root");
        ds.setPassword(env.getProperty("MYSQL_ROOT_PASSWORD"));
        ds.setInitialSize(5);
        ds.setMaxActive(10);
        ds.setMaxIdle(5);
        ds.setMinIdle(2);
        //ds.setValidationQuery("select now()");

        return ds;
    }

    public SQLExecutor executor(String key) {
        SQLExecutor exe = null;
        try {
            //java:comp/env/jdbc/testDB
            if (key == null || key.isEmpty()) key = UUID.randomUUID().toString();
            JDBConnectionPool.configure(key, createDataSource());
            exe = new SQLExecutor(JDBConnectionPool.connection(key));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Created DB Connections.");
        return exe;
    }
}
