package me.simran.config.PersistenceConfiguration;

//imports

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
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
import java.util.Properties;

@Configuration
@PropertySource({ "classpath:application.properties" })
@EnableJpaRepositories(
        basePackages = "me.simran.repository.thirdParty",
        entityManagerFactoryRef = "thirdPartyEntityManager",
        transactionManagerRef = "thirdPartyTransactionManager"
)
public class ThirdPartyPersistenceConfiguration {

    private final Environment env;

    public ThirdPartyPersistenceConfiguration(Environment env) {
        this.env = env;
    }

    @Bean
    @ConfigurationProperties(prefix="spring.thirddb-datasource")

    public DataSource thirdPartyDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean thirdPartyEntityManager() {
        final LocalContainerEntityManagerFactoryBean em
                = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(thirdPartyDataSource());
        em.setPackagesToScan("me.simran.entity.thirdParty");

        final HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);

        Properties properties = new Properties();
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL57Dialect");
        properties.put("hibernate.hbm2ddl.auto","create-drop");
        properties.put("hibernate.show_sql", "true");
        em.setJpaProperties(properties);
        return em;
    }

    @Bean
    public PlatformTransactionManager thirdPartyTransactionManager() {

        final JpaTransactionManager transactionManager
                = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(
                thirdPartyEntityManager().getObject());
        return transactionManager;
    }
}