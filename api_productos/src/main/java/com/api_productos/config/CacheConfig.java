package com.api_productos.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@Configuration
public class CacheConfig {

    @Value("${cache.ttl.producto}")
    private long productoTTL;

    @Value("${cache.ttl.inventarios}")
    private long inventariosTTL;

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory connectionFactory) {

        RedisCacheConfiguration defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig()
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));

        RedisCacheConfiguration shortTTLConfig = defaultCacheConfig.entryTtl(Duration.ofSeconds(productoTTL));
        RedisCacheConfiguration inventariosTTLConfig = defaultCacheConfig.entryTtl(Duration.ofSeconds(inventariosTTL)); // Configuración de inventarios
        RedisCacheConfiguration noTTLConfig = defaultCacheConfig.entryTtl(Duration.ZERO);

        return RedisCacheManager.builder(connectionFactory)
                .withCacheConfiguration("productos", shortTTLConfig)
                .withCacheConfiguration("inventarios", inventariosTTLConfig)
                .withCacheConfiguration("categorias", noTTLConfig)
                .build();
    }

}


