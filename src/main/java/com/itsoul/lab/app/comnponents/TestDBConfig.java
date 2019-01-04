package com.itsoul.lab.app.comnponents;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "testdbEntityManagerFactory",
        transactionManagerRef = "testdbTransactionManager",
        basePackages = {"com.itsoul.lab.app.repository"}
)
@PropertySource("classpath:application.properties")
public class TestDBConfig {

    @Value("${spring.datasource.driver-class-name}") String driverClassName;
    @Value("${spring.datasource.url}") String url;
    @Value("${spring.datasource.username}") String username;
    @Value("${spring.datasource.password}") String password;

    @Primary
    @Bean(name = "testdbDataSource")
    public DataSource dataSource(){

        DataSource dataSource = DataSourceBuilder
                .create()
                .username(username)
                .password(password)
                .url(url)
                .driverClassName(driverClassName)
                .build();
        return dataSource;
    }

    @Primary
    @Bean(name = "testdbEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            EntityManagerFactoryBuilder builder
            , @Qualifier("testdbDataSource") DataSource dataSource){

        return builder
                .dataSource(dataSource)
                .packages("com.itsoul.lab.app.beans")
                .persistenceUnit("testDB")
                .build();
    }

    @Primary
    @Bean(name = "testdbTransactionManager")
    public PlatformTransactionManager transactionManager(
            @Qualifier("testdbEntityManagerFactory") EntityManagerFactory entityManagerFactory){

        return new JpaTransactionManager(entityManagerFactory);
    }

}
