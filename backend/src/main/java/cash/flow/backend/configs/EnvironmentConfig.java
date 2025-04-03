package cash.flow.backend.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.cdimascio.dotenv.Dotenv;

@Configuration
public class EnvironmentConfig {

    @Bean
    public Dotenv dotenv() {
        return Dotenv
                .configure()
                .ignoreIfMissing() // Prevents errors if .env file is missing
                .systemProperties() // Loads from system environment variables
                .load();
    }

}
