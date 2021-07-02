package me.simran.config.PersistenceConfiguration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@PropertySource({ "classpath:application.properties" })
@EnableJpaRepositories(
        basePackages = {"me.simran.repository.Employee", "me.simran.repository.Pickup"},
        entityManagerFactoryRef = "localEntityManager",
        transactionManagerRef = "localTransactionManager"
)
public class LocalPersistenceConfiguration {

    private final Environment env;

    public LocalPersistenceConfiguration(Environment env) {
        this.env = env;
    }

    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean localEntityManager() {
        LocalContainerEntityManagerFactoryBean em
                = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(localDataSource());
        em.setPackagesToScan("me.simran.entity.models");

        HibernateJpaVendorAdapter vendorAdapter
                = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto",
                env.getProperty("hibernate.hbm2ddl.auto"));
        properties.put("hibernate.dialect",
                env.getProperty("hibernate.dialect"));
        em.setJpaPropertyMap(properties);

        return em;
    }

    @Primary
    @Bean
    public DataSource localDataSource() {

        DriverManagerDataSource dataSource
                = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/pickup_db?useSSL=false");
        dataSource.setUsername("root");
        dataSource.setPassword("");

        return dataSource;
    }

    @Primary
    @Bean
    public PlatformTransactionManager localTransactionManager() {

        JpaTransactionManager transactionManager
                = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(
                localEntityManager().getObject());
        return transactionManager;
    }
}