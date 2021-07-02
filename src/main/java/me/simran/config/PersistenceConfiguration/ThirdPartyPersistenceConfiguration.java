package me.simran.config.PersistenceConfiguration;

//imports

import org.springframework.context.annotation.Bean;
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
    public LocalContainerEntityManagerFactoryBean thirdPartyEntityManager() {
        LocalContainerEntityManagerFactoryBean em
                = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(thirdPartyDataSource());
        em.setPackagesToScan("me.simran.entity.thirdParty");

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto",
                env.getProperty("hibernate.hbm2ddl.auto"));
        properties.put("hibernate.dialect",
                env.getProperty("hibernate.dialect"));
        em.setJpaPropertyMap(properties);

        return em;
    }

    @Bean
    public DataSource thirdPartyDataSource() {

        DriverManagerDataSource dataSource
                = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/thirdparty_db?useSSL=false");
        dataSource.setUsername("root");
        dataSource.setPassword("");

        return dataSource;
    }

    @Bean
    public PlatformTransactionManager thirdPartyTransactionManager() {

        JpaTransactionManager transactionManager
                = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(
                thirdPartyEntityManager().getObject());
        return transactionManager;
    }
}