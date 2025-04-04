package cash.flow.backend.configs;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import io.github.cdimascio.dotenv.Dotenv;

@Configuration
public class DatabaseConfig {

    @Autowired
    private Dotenv dotenv;

    @Bean
    @Profile("!test")
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(dotenv.get("DATABASE_URL"));
        config.setUsername(dotenv.get("DATABASE_USERNAME"));
        config.setPassword(dotenv.get("DATABASE_PASSWORD"));
        // config.addDataSourceProperty("cachePrepStmts", "true");
        // config.addDataSourceProperty("prepStmtCacheSize", "250");
        // config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        return new HikariDataSource(config);
    }

    @Bean
    @Profile("test")
    public DataSource testDataSource() {
        HikariDataSource ds = new HikariDataSource();
        ds.setJdbcUrl("jdbc:h2:mem:;INIT=RUNSCRIPT FROM 'classpath:schema.sql';");

        return ds;
    }

    @Bean
    public ApplicationRunner initDatabase(DataSource dataSource) {
        return args -> {
            try {
                Connection connection = dataSource.getConnection();

                // FileReader fileReader = new FileReader("src/main/resources/schema.sql");

                // Read file

                InputStream inputStream = getClass().getClassLoader().getResourceAsStream("schema.sql");

                if (inputStream == null) {
                    throw new FileNotFoundException("Schema file not found in classpath");
                }

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader fileReader = new BufferedReader(inputStreamReader);

                // try (BufferedReader bufferedReader = new BufferedReader(fileReader)) {

                // Execute SQL commands from the file
                String line = null;
                String sqlCommand = "";

                while ((line = fileReader.readLine()) != null) {
                    sqlCommand += line.strip() + " ";
                }

                for (String command : sqlCommand.split(";")) {
                    if (!command.trim().isEmpty()) {
                        System.out.println("Executing command: " + command);
                        connection.createStatement().execute(command);
                    }
                }
                // }

            } catch (SQLException e) {
                throw new RuntimeException("Failed to connect to the database", e);
            } catch (FileNotFoundException e) {
                throw new RuntimeException("Schema file not found", e);
            } catch (IOException e) {
                throw new RuntimeException("Error reading schema file", e);
            }
        };
    }

}
