package com.cyf.malltiny.config;

import com.cyf.malltiny.common.config.BaseRedisConfig;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

/**Redis配置类
 * @author by cyf
 * @date 2020/9/13.
 */
@Configuration
@EnableCaching
public class RedisConfig extends BaseRedisConfig {
}
