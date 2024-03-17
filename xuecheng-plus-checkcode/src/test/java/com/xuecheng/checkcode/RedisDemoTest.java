package com.xuecheng.checkcode;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xuecheng.checkcode.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.Map;

@SpringBootTest
public class RedisDemoTest {
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Test
    void testString(){
        stringRedisTemplate.opsForValue().set("zs","虎哥");
        Object name = stringRedisTemplate.opsForValue().get("name");
        System.out.println("zs"+name);
    }

    private static final ObjectMapper mapper = new ObjectMapper();
    @Test
    void testobject() throws JsonProcessingException {
        User user = new User("猫哥", 22);
        String json = mapper.writeValueAsString(user);
        stringRedisTemplate.opsForValue().set("user:1",json);
        String jsonUser = stringRedisTemplate.opsForValue().get("user:1");
        User user1 = mapper.readValue(jsonUser, User.class);
        System.out.println("user1 = " + user1);
    }
    @Test
    void testHash(){
        stringRedisTemplate.opsForHash().put("user:3","name","狗哥");
        stringRedisTemplate.opsForHash().put("user:3","age","23");

        Map<Object, Object> entries = stringRedisTemplate.opsForHash().entries("user:3");
        System.out.println("entries = " + entries);
    }
}
