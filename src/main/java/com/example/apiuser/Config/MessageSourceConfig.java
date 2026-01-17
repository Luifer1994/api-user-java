package com.example.apiuser.Config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class MessageSourceConfig {

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

        try {
            // Buscamos todos los archivos messages.properties dentro de la carpeta i18n
            // classpath*:i18n/**/messages.properties busca recursivamente
            Resource[] resources = resolver.getResources("classpath*:i18n/**/messages.properties");
            List<String> basenames = new ArrayList<>();

            for (Resource resource : resources) {
                String uri = resource.getURI().toString();
                // Necesitamos extraer la ruta desde "i18n"
                // Ejemplo URI: file:/.../classes/i18n/messages/user/messages.properties
                // Queremos basename: classpath:i18n/messages/user/messages

                if (uri.contains("i18n/")) {
                    String cleanPath = uri.substring(uri.indexOf("i18n/"));
                    cleanPath = cleanPath.replace(".properties", "");
                    String basename = "classpath:" + cleanPath;

                    if (!basenames.contains(basename)) {
                        basenames.add(basename);
                        System.out.println("Loaded message bundle: " + basename);
                    }
                }
            }

            // Convertimos la lista a array
            messageSource.setBasenames(basenames.toArray(new String[0]));
            messageSource.setDefaultEncoding("UTF-8");

        } catch (IOException e) {
            throw new RuntimeException("Error loading message bundles dynamically", e);
        }

        return messageSource;
    }
}
