package com.example.abstractionizer.login.jwt3.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@AllArgsConstructor
public class RedisUtil {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;

    public boolean set(@NonNull String key, @NonNull Object obj, @NonNull Long duration, @NonNull TimeUnit timeUnit){
        try{
            this.redisTemplate.boundValueOps(key).set(obj, duration, timeUnit);
            return true;
        }catch(Exception var1){
            try{
                log.error("Write Obj failure, key: {} obj: {}", key, this.objectMapper.writeValueAsString(obj), var1);
            }catch(JsonProcessingException var2){
                log.error("Write Obj to json error: ", var2);
            }
        }
        return false;
    }

    public <T> T get(@NonNull String key, @NonNull Class<T> cls){
        Object obj;
        try{
            obj = this.redisTemplate.boundValueOps(key).get();
        }catch(Exception var3){
            log.error("Get Obj failed, key: {}", key, var3);
            return null;
        }
        return cls.cast(obj);
    }

    public boolean isKeyExists(@NonNull String key){
        if(key.isEmpty()){
            return false;
        }else{
            return !Objects.isNull(this.get(key, Object.class));
        }
    }

    public Long increment(@NonNull String key, @NonNull Long amount){
        return this.redisTemplate.boundValueOps(key).increment(amount);
    }

    public boolean deleteKey(@NonNull String key){
        return Objects.isNull(key) || this.redisTemplate.delete(key);
    }
}
