package com.bootcamp.project.project_yahoo_finance.infra;

import java.time.Duration;
import java.util.Set;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RedisHelper {

  private RedisTemplate<String, String> redisTemplate;
  private ObjectMapper objectMapper;

  public RedisHelper(RedisConnectionFactory factory,
      ObjectMapper objectMapper) {
    RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
    redisTemplate.setConnectionFactory(factory);
    redisTemplate.setKeySerializer(RedisSerializer.string());
    redisTemplate.setValueSerializer(RedisSerializer.json());
    redisTemplate.afterPropertiesSet();
    this.redisTemplate = redisTemplate;
    this.objectMapper = objectMapper;
  }

  public <T> T get(String key, Class<T> clazz) throws JsonProcessingException {
    String json = this.redisTemplate.opsForValue().get(key);
    return json == null ? null : this.objectMapper.readValue(json, clazz);
  }

  public <T> void set(String key, T obj) throws JsonProcessingException {
    String json = this.objectMapper.writeValueAsString(obj);
    redisTemplate.opsForValue().set(key, json);
  }

  public <T> void set(String key, T obj, Duration duration) throws JsonProcessingException {
    String json = this.objectMapper.writeValueAsString(obj);
    redisTemplate.opsForValue().set(key, json, duration);
  }

  public <T> void delete(String key) {
    redisTemplate.delete(key);
  }

  public <T> void deleteAll() {
    Set<String> keys = redisTemplate.keys("*");
    redisTemplate.delete(keys);
  }
}
