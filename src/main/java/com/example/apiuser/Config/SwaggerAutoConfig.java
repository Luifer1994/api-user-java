package com.example.apiuser.Config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.examples.Example;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

@Configuration
public class SwaggerAutoConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        Components components = new Components();
        ObjectMapper mapper = new ObjectMapper();
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

        try {
            // 1. Busca TODOS los .json en swagger/examples/ (y subcarpetas)
            Resource[] resources = resolver.getResources("classpath*:swagger/examples/**/*.json");

            for (Resource resource : resources) {
                String filename = resource.getFilename();
                if (filename == null) continue;

                // 2. Prefijo = nombre del archivo (ej: "create-user.json" -> "create-user")
                String prefix = filename.replace(".json", "");

                // 3. Lee el JSON
                JsonNode rootNode = mapper.readTree(resource.getInputStream());

                // 4. Registra cada clave como un ejemplo
                Iterator<Map.Entry<String, JsonNode>> fields = rootNode.fields();
                while (fields.hasNext()) {
                    Map.Entry<String, JsonNode> field = fields.next();
                    String key = field.getKey();       // ej: "success", "error_400"
                    JsonNode value = field.getValue(); // El contenido

                    Example example = new Example();
                    example.setValue(value);

                    // 5. Crea la referencia: "create-user-success"
                    String refName = prefix + "-" + key.replace("_", "-");
                    components.addExamples(refName, example);
                }
            }
        } catch (IOException e) {
            System.err.println("❌ Error Swagger: " + e.getMessage());
        }

        return new OpenAPI().components(components);
    }
}