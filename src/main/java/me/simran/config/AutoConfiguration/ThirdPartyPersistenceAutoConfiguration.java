package me.simran.config.AutoConfiguration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.sql.DataSource;

@Configuration
@PropertySource({ "classpath:application.properties" })
@EnableJpaRepositories(
        basePackages = "me.simran.repository.thirdParty",
        entityManagerFactoryRef = "thirdPartyEntityManager",
        transactionManagerRef = "thirdPartyTransactionManager"
)
public class ThirdPartyPersistenceAutoConfiguration {

    @Bean
    @ConfigurationProperties(prefix="spring.thirdparty.datasource")
    public DataSource thirdPartyDataSource() {
        return DataSourceBuilder.create().build();
    }

    // productEntityManager bean

    // productTransactionManager bean
}
