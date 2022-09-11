package com.cgd.mkt.salary_process.config;


import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(basePackages = "com.cgd.mkt.salary_process.repository.mis",
        entityManagerFactoryRef = "misEntityManagerFactory",
        transactionManagerRef= "misTransactionManager")

public class MisDataSourceConfiguration {
    @Bean
    @ConfigurationProperties("spring.datasource.mis")
    public DataSourceProperties misDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.mis.configuration")
    public DataSource misDataSource() {
        return misDataSourceProperties().initializeDataSourceBuilder()
                .type(HikariDataSource.class).build();
    }

    @Bean(name = "misEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean misEntityManagerFactory(
            EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(misDataSource())
                .packages("com.cgd.mkt.salary_process.model.mis")
                .build();
    }

    @Bean
    public PlatformTransactionManager misTransactionManager(
            final @Qualifier("misEntityManagerFactory") LocalContainerEntityManagerFactoryBean misEntityManagerFactory) {
        return new JpaTransactionManager(misEntityManagerFactory.getObject());
    }
}
