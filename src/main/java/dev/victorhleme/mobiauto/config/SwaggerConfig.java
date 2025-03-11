package dev.victorhleme.mobiauto.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.function.Supplier;

import static dev.victorhleme.mobiauto.security.SecurityConstants.SWAGGER_BEARER_AUTH;
import static dev.victorhleme.mobiauto.security.SecurityConstants.TOKEN_TYPE;

@OpenAPIDefinition(servers = {@Server(url = "/", description = "Default Server URL")})
@Configuration
public class SwaggerConfig {

    @Value("${swagger.version}")
    private String version;

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("MobiAuto Backend")
                .version(version)
                .description("This API exposes endpoints to manage the MobiAuto Oportunidade feature set."))
            .addSecurityItem(
                securityRequirementFor(SWAGGER_BEARER_AUTH)
            )
            .components(
                componentsFor(SWAGGER_BEARER_AUTH)
            );
    }

    private final Map<String, Supplier<SecurityScheme>> securitySchemes = Map.of(
        SWAGGER_BEARER_AUTH, this::createJWTScheme
    );

    protected Components componentsFor(String... schemas) {
        Components components = new Components();
        for (String schema : schemas) {
            if (securitySchemes.containsKey(schema))
                components.addSecuritySchemes(schema, securitySchemes.get(schema).get());
        }
        return components;
    }

    protected SecurityRequirement securityRequirementFor(String... schemas) {
        SecurityRequirement requirement = new SecurityRequirement();
        for (String schema : schemas) {
            if (securitySchemes.containsKey(schema))
                requirement.addList(schema);
        }
        return requirement;
    }

    private SecurityScheme createJWTScheme() {
        return new SecurityScheme().type(SecurityScheme.Type.HTTP)
            .bearerFormat(TOKEN_TYPE)
            .scheme("bearer");
    }
}
