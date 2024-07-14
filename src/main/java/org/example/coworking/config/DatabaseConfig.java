package org.example.coworking.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Configuration
@PropertySource("classpath:application.yml")
public class DatabaseConfig {

    @Value("${db.url}")
    private String url;

    @Value("${db.username}")
    private String username;

    @Value("${db.password}")
    private String password;

    @Bean
    public static PropertySourcesPlaceholderConfigurer properties() {
        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        configurer.setProperties(yamlProperties().getObject());
        return configurer;
    }

    @Bean
    public static YamlPropertiesFactoryBean yamlProperties() {
        YamlPropertiesFactoryBean bean = new YamlPropertiesFactoryBean();
        bean.setResources(new ClassPathResource("application.yml"));
        return bean;
    }

    public Connection connection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    @Bean
    public DatabaseConnectionProvider databaseConnectionProvider() {
        return new DatabaseConnectionProviderImpl(url, username, password);
    }
}
