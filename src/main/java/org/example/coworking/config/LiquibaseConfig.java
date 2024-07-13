package org.example.coworking.config;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import javax.annotation.PostConstruct;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@Configuration
@PropertySource("classpath:application.yml")
public class LiquibaseConfig {

    @Value("${liquibase.changelog}")
    private String changeLogFile;

    @Value("${liquibase.default-schema}")
    private String defaultSchema;

    @Value("${liquibase.liquibase-schema}")
    private String liquibaseSchema;

    private final DatabaseConfig databaseConfig;

    @Autowired
    public LiquibaseConfig(DatabaseConfig databaseConfig) {
        this.databaseConfig = databaseConfig;
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer properties() {
        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        configurer.setProperties(yamlProperties().getObject());
        return configurer;
    }

    @Bean
    public static YamlPropertiesFactoryBean yamlProperties() {
        return DatabaseConfig.yamlProperties();
    }

    @Bean
    public static Liquibase liquibase(DatabaseConfig databaseConfig,
                                      @Value("${liquibase.changelog}") String changeLogFile,
                                      @Value("${liquibase.default-schema}") String defaultSchema,
                                      @Value("${liquibase.liquibase-schema}") String liquibaseSchema) throws LiquibaseException, SQLException {
        Connection connection = databaseConfig.connection();
        Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));

        String createSchemaQuery = "CREATE SCHEMA IF NOT EXISTS liquibase_logs;";
        try (Statement statement = connection.createStatement()) {
            statement.execute(createSchemaQuery);
        }

        database.setDefaultSchemaName(defaultSchema);
        database.setLiquibaseSchemaName(liquibaseSchema);

        Liquibase liquibase = new Liquibase(changeLogFile, new ClassLoaderResourceAccessor(), database);
        liquibase.update();
        return liquibase;
    }

    @PostConstruct
    public void runLiquibase() {
        try {
            liquibase(databaseConfig, changeLogFile, defaultSchema, liquibaseSchema);
        } catch (LiquibaseException | SQLException e) {
        }
    }
}
