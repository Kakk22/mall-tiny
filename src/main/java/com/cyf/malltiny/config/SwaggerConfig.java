package com.cyf.malltiny.config;

import com.cyf.malltiny.common.config.BaseSwaggerConfig;
import com.cyf.malltiny.common.domain.SwaggerProperties;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**Swagger API文档相关配置
 * @author by cyf
 * @date 2020/9/13.
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig extends BaseSwaggerConfig {

    @Override
    public SwaggerProperties swaggerProperties() {
        return SwaggerProperties.builder()
                .apiBasePackage("com.cyf.malltiny.modules")
                .title("mall-tiny项目骨架")
                .description("mall-tiny项目骨架相关接口文档")
                .contactName("cyf")
                .version("1.0")
                .enableSecurity(true)
                .build();
    }
}
