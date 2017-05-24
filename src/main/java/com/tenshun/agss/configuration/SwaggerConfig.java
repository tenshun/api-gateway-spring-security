package com.tenshun.agss.configuration;

import com.fasterxml.classmate.TypeResolver;
import com.tenshun.agss.rest.Gateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.schema.WildcardType;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.LocalDate;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static springfox.documentation.schema.AlternateTypeRules.newRule;


/**
 * Swagger configuration for Spring Boot and Spring MVC
 * @EnableSwagger2 : Enables Springfox swagger 2
 */

@Configuration
@EnableSwagger2
@ComponentScan(basePackageClasses = {
        Gateway.class
})
public class SwaggerConfig {


    /**
     * @code select() returns an instance of ApiSelectorBuilder to give fine grained control over the endpoints exposed via swagger.
     *
     * @code apis() allows selection of RequestHandler's using a predicate.
     * The example here uses an any predicate (default).
     * Out of the box predicates provided are any, none, withClassAnnotation, withMethodAnnotation and basePackage.
     *
     * @code paths() allows selection of Path's using a predicate. The example here uses an any predicate (default)
     *
     * @code build() The selector requires to be built after configuring the api and path selectors.
     * Out of the box we provide predicates for regex, ant, any, none
     *
     * @code additionalModels() Are there models in the application that are not "reachable"?
     * Not reachable is when we have models that we would like to be described but aren’t explicitly used in any operation.
     * An example of this is an operation that returns a model serialized as a string.
     * We do want to communicate the expectation of the schema for the string. This is a way to do exactly that.
     * There are plenty of more options to configure the Docket. This should provide a good start.
     *
     * @return Docket, Springfox’s, primary api configuration mechanism is initialized for swagger specification 2.0
     */

    @Bean
    public Docket gatewayAPI() { //
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .pathMapping("/")
                .directModelSubstitute(LocalDate.class,
                        String.class)
                .genericModelSubstitutes(ResponseEntity.class)
                .alternateTypeRules(
                        newRule(typeResolver.resolve(DeferredResult.class,
                                typeResolver.resolve(ResponseEntity.class, WildcardType.class)),
                                typeResolver.resolve(WildcardType.class)))
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET,
                        newArrayList(new ResponseMessageBuilder()
                                .code(500)
                                .message("500 message")
                                .responseModel(new ModelRef("Error"))
                                .build()))
                .securitySchemes(newArrayList(apiKey()))
                .securityContexts(newArrayList(securityContext()))
                .enableUrlTemplating(true)
                .globalOperationParameters(
                        newArrayList(new ParameterBuilder()
                                .name("someGlobalParameter")
                                .description("Description of someGlobalParameter")
                                .modelRef(new ModelRef("string"))
                                .parameterType("query")
                                .required(true)
                                .build()))
                .tags(new Tag("Gateway API service", "All apis"));
                //.additionalModels(typeResolver.resolve(AdditionalModel.class));


    }

    @Autowired
    private TypeResolver typeResolver;

    /**
     * @return 	Here we use ApiKey as the security schema that is identified by the name mykey
     */

    private ApiKey apiKey() {
        return new ApiKey("mykey", "api_key", "header");
    }

    /**
     *
     * @code forPaths(PathSelectors...) Selector for the paths this security context applies to.
     * @return SecurityContext todo
     */
    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex("/anyPath.*"))
                .build();
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope
                = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return newArrayList(
                new SecurityReference("mykey", authorizationScopes));
    }

    @Bean
    SecurityConfiguration security() {
        return new SecurityConfiguration(
                "test-app-client-id",
                "test-app-client-secret",
                "test-app-realm",
                "test-app",
                "apiKey",
                ApiKeyVehicle.HEADER,
                "api_key",
                "," /*scope separator*/);
    }

    @Bean
    UiConfiguration uiConfig() {
        return new UiConfiguration(
                "validatorUrl",// url
                "none",       // docExpansion          => none | list
                "alpha",      // apiSorter             => alpha
                "schema",     // defaultModelRendering => schema
                UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS,
                false,        // enableJsonEditor      => true | false
                true,         // showRequestHeaders    => true | false
                60000L);      // requestTimeout => in milliseconds, defaults to null (uses jquery xh timeout)
    }
}
