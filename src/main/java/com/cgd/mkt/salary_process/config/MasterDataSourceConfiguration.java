package com.cgd.mkt.salary_process.config;


import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(basePackages = "com.cgd.mkt.salary_process.repository.master",
        entityManagerFactoryRef = "masterEntityManagerFactory",
        transactionManagerRef= "masterTransactionManager")

public class MasterDataSourceConfiguration {
    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource.master")
    public DataSourceProperties masterDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource.master.configuration")
    public DataSource masterDataSource() {
         return masterDataSourceProperties().initializeDataSourceBuilder()
         .type(HikariDataSource.class).build();
    }

    @Primary
    @Bean(name = "masterEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean masterEntityManagerFactory(
    EntityManagerFactoryBuilder builder) {
         return builder
         .dataSource(masterDataSource())
         .packages("com.cgd.mkt.salary_process.model.master")
         .build();
    }

    @Bean
    @Primary
    public PlatformTransactionManager masterTransactionManager(
            final @Qualifier("masterEntityManagerFactory") LocalContainerEntityManagerFactoryBean masterEntityManagerFactory) {
        return new JpaTransactionManager(masterEntityManagerFactory.getObject());
    }

}
