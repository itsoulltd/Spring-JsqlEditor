package com.itsoul.lab.app.comnponents;

import com.it.soul.lab.connect.JDBConnectionPool;
import com.it.soul.lab.sql.SQLExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.ResourceBundle;

@Component
public class CMDRunner implements CommandLineRunner {

    @Autowired
    private Environment env;

    @Value("${app.microservice.basename}")
    private String baseName;

    @EventListener({ContextRefreshedEvent.class})
    public void refreshEventListener(){

        //GeoTracker.shared().loadProperties(ResourceBundle.getBundle(baseName));
        //log.info("API Gateway:" + WebResource.API_GATEWAY.value());

        System.out.println("BASE_FILENAME: " + env.getProperty("BASE_FILENAME"));
        System.out.println("BASE_FILENAME-2: " + baseName);

        Map<String, String> sysEnv = System.getenv();
        System.out.println("BASE_FILENAME-3: " + sysEnv.get("BASE_FILENAME"));

        System.out.println("MYSQL_DATABASE_HOST: " + sysEnv.get("MYSQL_DATABASE_HOST"));

        System.out.println("com.itsoul.lab.api.gateway: " + sysEnv.get("com.itsoul.lab.api.gateway"));
    }

//    @Autowired
//    @Qualifier("JndiTemplate")
//    private SQLExecutor executor;

    @Autowired
    @Qualifier("JDBConnectionPool")
    private SQLExecutor executor2;

    @Override
    public void run(String... args) throws Exception {

        //Deploy Tables
        System.out.println("Configure Tables...Started");

        String createPassenger = "CREATE TABLE IF NOT EXISTS Passenger (" +
                "id int auto_increment primary key" +
                ", name varchar(1024) null" +
                ", age  int default '18' null" +
                ", sex varchar(12) null" +
                ", constraint Passenger_id_uindex unique (id));";
        executor2.executeDDLQuery(createPassenger);

        String createParson = "CREATE TABLE IF NOT EXISTS Person (uuid varchar(512) not null primary key" +
                ", name varchar(512) null, age int null" +
                ", active tinyint(1) null" +
                ", salary double null,dob datetime null,height float null" +
                ",createDate timestamp null, dobDate date null, createTime time null);";
        executor2.executeDDLQuery(createParson);

        System.out.println("Configure Tables...End");
        //
    }

    private void testing(SQLExecutor exe){
        //Testing
        try {
            ResultSet set = exe.executeSelect("Select * from Passenger");
            exe.displayResultSet(set);
            System.out.println("Table Configuration ended");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        //
    }
}
