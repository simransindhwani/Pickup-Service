package me.simran.config.AutoConfiguration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.sql.DataSource;


@Configuration
@PropertySource({ "classpath:application.properties" })
@EnableJpaRepositories(
        basePackages = {"me.simran.repository.Employee", "me.simran.repository.Pickup"},
        entityManagerFactoryRef = "localEntityManager",
        transactionManagerRef = "localTransactionManager"
)

public class LocalPersistenceAutoConfiguration {

    @Primary
    @Bean
    @ConfigurationProperties(prefix="spring.datasource")
    public DataSource localDataSource() {
        return DataSourceBuilder.create().build();
    }
    // userEntityManager bean

    // userTransactionManager bean
}