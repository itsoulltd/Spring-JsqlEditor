package com.itsoul.lab.app;

import com.it.soul.lab.connect.JDBConnectionPool;
import com.it.soul.lab.sql.SQLExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CMDRunner implements CommandLineRunner {


    @Autowired
    @Qualifier("JndiTemplate")
    private SQLExecutor executor;

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
        executor.executeDDLQuery(createPassenger);

        String createParson = "create table Person(uuid varchar(512) not null primary key" +
                ", name varchar(512) null, age int null" +
                ", active tinyint(1) null" +
                ", salary double null,dob datetime null,height float null" +
                ",createDate timestamp null, dobDate date null, createTime time null);";
        executor2.executeDDLQuery(createParson);

        System.out.println("Configure Tables...End");
        //

        System.out.println("1st....");
        testing(executor);
        System.out.println();
        System.out.println("2nd ..... ");
        testing(executor2);

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
