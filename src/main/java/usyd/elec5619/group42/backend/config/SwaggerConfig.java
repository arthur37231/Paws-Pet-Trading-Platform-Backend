package usyd.elec5619.group42.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableOpenApi
public class SwaggerConfig {
    private static final Contact APP_CONTACT = new Contact("", "", "");

    private Docket produceDocket(String groupName, String path) {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(new ApiInfo(
                        "ELEC5619 Project - Pet Trading",
                        "2021 Semester 2 ELEC5619 Project by Group 42 - Pet Trading",
                        "0.0.1",
                        "urn:tos",
                        APP_CONTACT,
                        "Apache 2.0",
                        "http://www.apache.org/licenses/LICENSE-2.0",
                        new ArrayList<>()
                ))
                .groupName(groupName)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.ant(path))
                .build()
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts());
    }

    @Bean
    public Docket userApi() {
        return produceDocket("2 - User", "/user/**");
    }

    @Bean
    public Docket authApi() {
        return produceDocket("1 - Auth", "/auth/**");
    }

    @Bean
    public Docket postApi() {
        return produceDocket("3 - PetPost", "/petPost/**");
    }

    @Bean
    public Docket categoryApi() {
        return produceDocket("4 - PetCategory", "/category/**");
    }

    private List<SecurityScheme> securitySchemes() {
        List<SecurityScheme> schemes = new ArrayList<>();
        schemes.add(new ApiKey("Authorization", "Authorization", "header"));
        return schemes;
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;

        List<SecurityReference> auth = new ArrayList<>();
        auth.add(new SecurityReference("Authorization", authorizationScopes));

        return auth;
    }

    private List<SecurityContext> securityContexts() {
        List<SecurityContext> contexts = new ArrayList<>();
        contexts.add(
                SecurityContext.builder()
                        .securityReferences(defaultAuth())
                        .forPaths(PathSelectors.regex("^(?!auth).*$"))
                        .build()
        );
        return contexts;
    }
}
