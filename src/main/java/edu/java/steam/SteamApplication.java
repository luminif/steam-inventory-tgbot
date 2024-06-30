package edu.java.steam;

import edu.java.steam.bot.configuration.ApplicationConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(ApplicationConfig.class)
public class SteamApplication {
	public static void main(String[] args) {
		SpringApplication.run(SteamApplication.class, args);
	}
}
