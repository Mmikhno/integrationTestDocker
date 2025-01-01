package ru.netology.springFirst.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.netology.springFirst.profile.DevProfile;
import ru.netology.springFirst.profile.ProductionProfile;
import ru.netology.springFirst.profile.SystemProfile;

@Configuration

public class JavaConfig {
    @Bean
    @ConditionalOnProperty(prefix = "netology.profile", name = "dev", havingValue = "true",matchIfMissing = true)
    public SystemProfile devProfile() {
        return new DevProfile();
    }

    @ConditionalOnProperty(prefix = "netology.profile", name = "dev", havingValue = "false")
    @Bean
    public SystemProfile prodProfile() {
        return new ProductionProfile();
    }
}
