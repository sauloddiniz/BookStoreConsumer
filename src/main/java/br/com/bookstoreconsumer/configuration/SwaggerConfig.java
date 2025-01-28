package br.com.bookstoreconsumer.configuration;

import org.springdoc.core.configuration.SpringDocConfiguration;
import org.springdoc.core.configuration.SpringDocUIConfiguration;
import org.springdoc.core.properties.SpringDocConfigProperties;
import org.springdoc.core.properties.SwaggerUiConfigProperties;
import org.springdoc.core.providers.ObjectMapperProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

@Configuration
public class SwaggerConfig {


    @Bean
    @Qualifier("springDocConfiguration")
    SpringDocConfiguration springDocConfiguration() {
        return new SpringDocConfiguration();
    }

    @Bean
    @Qualifier("springDocConfigProperties")
    SpringDocConfigProperties springDocConfigProperties() {
        return new SpringDocConfigProperties();
    }

    @Bean
    @Qualifier("objectMapperProvider")
    ObjectMapperProvider objectMapperProvider(SpringDocConfigProperties springDocConfigProperties) {
        return new ObjectMapperProvider(springDocConfigProperties);
    }

    @Bean
    @Qualifier("springDocUIConfiguration")
    SpringDocUIConfiguration springDocUiConfiguration(Optional<SwaggerUiConfigProperties> optionalSwaggerUiConfigProperties) {
        return new SpringDocUIConfiguration(optionalSwaggerUiConfigProperties);
    }
}
