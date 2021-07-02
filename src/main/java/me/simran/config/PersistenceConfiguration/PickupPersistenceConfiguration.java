package me.simran.config.PersistenceConfiguration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Properties;

@Configuration
@PropertySource({ "classpath:application.properties" })
@EnableJpaRepositories(
        basePackages = {"me.simran.repository.Employee", "me.simran.repository.Pickup"},
        entityManagerFactoryRef = "pickupEntityManager",
        transactionManagerRef = "pickupTransactionManager"
)
public class PickupPersistenceConfiguration {

    private final Environment env;

    public PickupPersistenceConfiguration(Environment env) {
        this.env = env;
    }

    @Primary
    @Bean
    @ConfigurationProperties(prefix="spring.datasource")
    public DataSource pickupDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean pickupEntityManager() {
        final LocalContainerEntityManagerFactoryBean em
                = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(pickupDataSource());
        em.setPackagesToScan("me.simran.entity.models");

        final HibernateJpaVendorAdapter vendorAdapter
                = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);

        Properties properties = new Properties();
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL57Dialect");
        properties.put("hibernate.hbm2ddl.auto","create-drop");
        properties.put("hibernate.show_sql", "true");
        em.setJpaProperties(properties);

        return em;
    }


    @Primary
    @Bean
    public PlatformTransactionManager pickupTransactionManager() {

        final JpaTransactionManager transactionManager
                = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(
                pickupEntityManager().getObject());
        return transactionManager;
    }
}