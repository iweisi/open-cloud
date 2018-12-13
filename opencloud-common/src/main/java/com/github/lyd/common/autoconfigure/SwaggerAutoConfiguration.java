package com.github.lyd.common.autoconfigure;

import com.github.lyd.common.utils.DateUtils;
import com.github.lyd.common.utils.RandomValueUtils;
import com.github.lyd.common.utils.SpringContextHolder;
import com.google.common.collect.Lists;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.i18n.LocaleContextHolder;
import springfox.documentation.builders.*;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger2.configuration.Swagger2DocumentationConfiguration;

import java.util.*;

/**
 * @author liuyadu
 */
@Slf4j
@Configuration
@EnableConfigurationProperties({SwaggerProperties.class})
@ConditionalOnProperty(prefix = "opencloud.swagger2", name = "enabled", havingValue = "true")
@Import({Swagger2DocumentationConfiguration.class})
public class SwaggerAutoConfiguration {
    private SwaggerProperties swaggerProperties;
    private static  final String SCOPE_PREFIX="scope.";
    private static Locale locale = LocaleContextHolder.getLocale();

    public SwaggerAutoConfiguration(GatewayProperties gatewayProperties, SwaggerProperties swaggerProperties) {
        this.swaggerProperties = swaggerProperties;
        log.debug("文档配置:{}", swaggerProperties);
        log.debug("文档安全配置:{}", gatewayProperties);
    }


    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .globalOperationParameters(parameters())
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(Collections.singletonList(securityScheme()));
    }

    private List<Parameter> parameters() {
        ParameterBuilder builder = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<Parameter>();
        builder.name("Authorization").description("公共参数:认证token")
                .modelRef(new ModelRef("string")).parameterType("header")
                .required(false);
        pars.add(builder.build());
        builder.name("clientId").description("公共参数:客户端Id")
                .modelRef(new ModelRef("string")).parameterType("form")
                .required(false);
        pars.add(builder.build());
        builder.name("nonce").description("公共参数:随机字符串")
                .defaultValue(RandomValueUtils.uuid())
                .modelRef(new ModelRef("string")).parameterType("form")
                .required(false);
        pars.add(builder.build());
        builder.name("timestamp").description("公共参数:请求的时间,格式:yyyyMMddHHmmss")
                .defaultValue(DateUtils.getTimestampStr())
                .modelRef(new ModelRef("string")).parameterType("form")
                .required(false);
        pars.add(builder.build());
        builder.name("signType").description("公共参数:签名方式:MD5(默认)、SHA256.")
                .modelRef(new ModelRef("string")).parameterType("form")
                .allowableValues(new AllowableListValues(Arrays.asList(new String[]{"MD5", "SHA256"}), "string"))
                .required(true);
        pars.add(builder.build());
        builder = new ParameterBuilder();
        builder.name("sign").description("公共参数:数字签名")
                .modelRef(new ModelRef("string")).parameterType("form")
                .defaultValue("")
                .required(false);
        pars.add(builder.build());
        return pars;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(swaggerProperties.getTitle())
                .description(swaggerProperties.getDescription())
                .version("1.0")
                .build();
    }

    @Bean
    UiConfiguration uiConfig() {
        return new UiConfiguration(null, "list", "alpha", "schema",
                UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS, false, true, 60000L);
    }


    /***
     * oauth2配置
     * 需要增加swagger授权回调地址
     * http://localhost:8888/webjars/springfox-swagger-ui/o2c.html
     * @return
     */
    @Bean
    SecurityScheme securityScheme() {
        return new OAuthBuilder()
                .name("OAuth2")
                .scopes(scopes())
                .grantTypes(grantTypes())
                .build();
    }


    private List<AuthorizationScope> scopes() {
        List<String> scopes = Lists.newArrayList();
        List list = Lists.newArrayList();
        MessageSource messageSource = SpringContextHolder.getBean(MessageSource.class);
        if (swaggerProperties.getScope() != null) {
            scopes.addAll(Arrays.asList(swaggerProperties.getScope().split(",")));
        }
        scopes.forEach(s -> {
            list.add(new AuthorizationScope(s, messageSource.getMessage(SCOPE_PREFIX+s,null,s,locale)));
        });
        return list;
    }

    @Bean
    public SecurityConfiguration security() {
        return new SecurityConfiguration(swaggerProperties.getClientId(),
                swaggerProperties.getClientSecret(),
                "realm", swaggerProperties.getClientId(),
                "", ApiKeyVehicle.HEADER, "", ",");
    }

    @Bean
    List<GrantType> grantTypes() {
        List<GrantType> grantTypes = new ArrayList<>();
        TokenRequestEndpoint tokenRequestEndpoint = new TokenRequestEndpoint(
                swaggerProperties.getUserAuthorizationUri(),
                swaggerProperties.getClientId(), swaggerProperties.getClientSecret());
        TokenEndpoint tokenEndpoint = new TokenEndpoint(swaggerProperties.getAccessTokenUri(), "access_token");
        grantTypes.add(new AuthorizationCodeGrant(tokenRequestEndpoint, tokenEndpoint));
        return grantTypes;
    }


}
