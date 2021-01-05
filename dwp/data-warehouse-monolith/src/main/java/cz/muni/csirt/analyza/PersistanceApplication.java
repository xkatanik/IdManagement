package cz.muni.csirt.analyza;

import cz.muni.csirt.analyza.util.config.StorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@EnableJpaRepositories("cz.muni.csirt.analyza.repository")
@EntityScan("cz.muni.csirt.analyza.entity")
@EnableConfigurationProperties(StorageProperties.class)
@EnableWebMvc
@Configuration
public class PersistanceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PersistanceApplication.class, args);
    }
}