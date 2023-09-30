package com.app.security.datasource;


import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Map;

@EnableTransactionManagement
@Configuration
@PropertySource({"classpath:persistence-multiple-db-boot.properties"})
@EnableJpaRepositories(basePackages = "com.app.security.repository.users",
        entityManagerFactoryRef = "localContainerEntityManagerFactoryBeanUser",
        transactionManagerRef = "transactionManagerUser")
public class UserDataSource {

    private final Environment env;
    public UserDataSource(Environment env) {
        super();
        this.env = env;
    }
    @Primary
    @Bean(name = "dataSourceUser")
    @ConfigurationProperties(prefix = "user.datasource")
    public DataSource dataSourceUser(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("users.datasource.class-name"));
        dataSource.setUrl(env.getProperty("users.datasource.jdbc-url"));
        dataSource.setUsername(env.getProperty("users.datasource.username"));
        dataSource.setPassword(env.getProperty("users.datasource.password"));
        return dataSource;
    }
    @Primary
    @Bean(name = "localContainerEntityManagerFactoryBeanUser")
    public LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBeanUser(
            EntityManagerFactoryBuilder builder, @Qualifier("dataSourceUser") DataSource dataSource){
        Map<String, Object> props = CoreDataSource.getHibernateProperties(env);
        return builder
                .dataSource(dataSource)
                .properties(props)
                .packages("com.app.security.domain.user")
                .persistenceUnit("User")
                .build();
    }
    @Primary
    @Bean(name = "transactionManagerUser")
    public PlatformTransactionManager transactionManagerUser(
            @Qualifier("localContainerEntityManagerFactoryBeanUser")
            EntityManagerFactory entityManagerFactory){
        return new JpaTransactionManager();
    }

}
