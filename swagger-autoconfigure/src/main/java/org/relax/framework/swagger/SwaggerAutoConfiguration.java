package org.relax.framework.swagger;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@ConditionalOnClass(Docket.class)
@ConditionalOnMissingBean(Docket.class)
@EnableSwagger2
@EnableConfigurationProperties(SwaggerProperties.class)
public class SwaggerAutoConfiguration {

    @Bean
    public Docket docket(SwaggerProperties apiProperties) {
        ParameterBuilder parameterBuilder = new ParameterBuilder();
        List<Parameter> parameters = new ArrayList<>();
        for (ParameterProperties parameterProperties : apiProperties.getGlobalParameters()) {
            parameters.add(parameterBuilder
                    .name(parameterProperties.getName())
                    .description(parameterProperties.getDescription())
                    .modelRef(new ModelRef(parameterProperties.getModelRef()))
                    .required(parameterProperties.isRequired())
                    .parameterType(parameterProperties.getParameterType())
                    .build()
            );
        }
        return new Docket(DocumentationType.SWAGGER_2)
                .forCodeGeneration(true)
                .apiInfo(apiInfo(apiProperties))
                .select()
                    .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                    .paths(PathSelectors.any())
                    .build()
                .globalOperationParameters(parameters);
    }

    private ApiInfo apiInfo(SwaggerProperties apiProperties) {
        return new ApiInfo(apiProperties.getTitle(),
                apiProperties.getDescription(),
                apiProperties.getVersion(),
                apiProperties.getTermsOfServiceUrl(),
                apiProperties.getContact(),
                apiProperties.getLicense(),
                apiProperties.getLicenseUrl(),
                apiProperties.getVendorExtensions());
    }
}
