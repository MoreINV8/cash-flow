package cash.flow.backend.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.cdimascio.dotenv.Dotenv;

@Configuration
public class EnvironmentConfig {

    @Bean
    public Dotenv dotenv() {
        return Dotenv.load();
    }
    
}
