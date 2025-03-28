package com.myblog;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.myblog.jpa")
public class JpaConfiguration {

    @Value("${DB_URL}")
    private String dbUrl;

    @Value("${DB_USER}")
    private String dbUser;

    @Value("${DB_PASSWORD}")
    private String dbPassword;

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            @Autowired DataSource dataSource) {

        LocalContainerEntityManagerFactoryBean emfb = new LocalContainerEntityManagerFactoryBean();
        emfb.setDataSource(dataSource);
        emfb.setPackagesToScan("com.myblog.entity");
        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        emfb.setJpaVendorAdapter(vendorAdapter);


        Properties jpaProperties = new Properties();
        jpaProperties.put("hibernate.hbm2ddl.auto", "update");
        jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        emfb.setJpaProperties(jpaProperties);

        return emfb;
    }

    @Bean
    public PlatformTransactionManager transactionManager(
            @Autowired EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/blog_db");
        dataSource.setUsername("blog_user");
        dataSource.setPassword("blog_password");
        return dataSource;
    }
}

