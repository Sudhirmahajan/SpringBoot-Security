package com.app.security.datasource;


import jakarta.persistence.EntityManagerFactory;
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
import java.util.HashMap;
import java.util.Map;

@EnableTransactionManagement
@Configuration
@PropertySource({"classpath:persistence-multiple-db-boot.properties"})
@EnableJpaRepositories(basePackages = "com.app.security.repository.core",
        entityManagerFactoryRef = "localContainerEntityManagerFactoryBeanCore",
        transactionManagerRef = "transactionManagerCore")
public class CoreDataSource {

    private final Environment env;
    public CoreDataSource(Environment env){
        super();
        this.env = env;
    }


    @Bean(name = "dataSourceCore")
    @ConfigurationProperties(prefix = "core.datasource")
    public DataSource dataSourceCore() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("core.datasource.class-name"));
        dataSource.setUrl(env.getProperty("core.datasource.jdbc-url"));
        dataSource.setUsername(env.getProperty("core.datasource.username"));
        dataSource.setPassword(env.getProperty("core.datasource.password"));
        return dataSource;
    }

    @Bean(name = "localContainerEntityManagerFactoryBeanCore")
    public LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBeanCore
            (EntityManagerFactoryBuilder builder,@Qualifier("dataSourceCore") DataSource dataSource){
        Map<String, Object> prop = getHibernateProperties(env);
        return builder
                .dataSource(dataSource)
                .properties(prop)
                .packages("com.app.security.domain.core")
                .persistenceUnit("Core")
                .build();
    }

    public static Map<String, Object> getHibernateProperties(Environment env) {
        Map<String, Object> properties = new HashMap<>();
        properties.put("spring.jpa.properties.hibernate.dialect", env.getProperty("spring.jpa.properties.hibernate.dialect"));
        properties.put("spring.jpa.properties.hibernate.show_sql", env.getProperty("spring.jpa.properties.hibernate.show_sql"));
        properties.put("spring.jpa.properties.hibernate.use_sql_comments", env.getProperty("spring.properties.jpa.hibernate.use_sql_comments"));
        properties.put("spring.jpa.properties.hibernate.format_sql", env.getProperty("spring.jpa.properties.hibernate.format_sql"));
        properties.put("spring.jpa.properties.hibernate.hbm2ddl.auto", env.getProperty("spring.jpa.properties.hibernate.hbm2ddl.auto"));
        return properties;
    }


    @Bean(name = "transactionManagerCore")
    public PlatformTransactionManager transactionManagerCore(
            @Qualifier("localContainerEntityManagerFactoryBeanCore")
             EntityManagerFactory entityManagerFactory){
        return new JpaTransactionManager(entityManagerFactory);
    }


}
