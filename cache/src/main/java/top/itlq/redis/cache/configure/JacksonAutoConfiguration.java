package top.itlq.redis.cache.configure;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.io.IOException;
import java.time.Instant;

/**
 * 结合spring-boot提供的jackson自动配置，添加自定义的jackson自动配置
 * @see org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration
 */
@Configuration
@ConditionalOnClass(ObjectMapper.class)
public class JacksonAutoConfiguration {

    @Configuration
    static class JacksonObjectMapperConfiguration {
        /**
         * @ConditionalOnMissingBean 使其覆盖springboot内部的且等待其他配置其build的bean加载完成才加载它；
         * @return
         */
        @Bean
        @Primary
        @ConditionalOnMissingBean
        public ObjectMapper jacksonObjectMapper() {
            ObjectMapper objectMapper = new ObjectMapper();

            SimpleModule module = new SimpleModule();
            module.addSerializer(Instant.class, new InstantJsonComponent.InstantJsonSerializer());
            module.addDeserializer(Instant.class, new InstantJsonComponent.InstantJsonDeserializer());

            objectMapper.registerModule(module);
            return objectMapper;
        }
    }


    @JsonComponent // 这个注解可以用于Jackson2ObjectBuilder自动增加module创建，但是这里没用web项目，所以暂无用
    static class InstantJsonComponent{
        public static class InstantJsonSerializer extends JsonSerializer<Instant>{

            @Override
            public void serialize(Instant value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
                gen.writeNumber(value.toEpochMilli());
            }
        }
        public static class InstantJsonDeserializer extends JsonDeserializer<Instant>{

            @Override
            public Instant deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
                return Instant.ofEpochMilli(p.getLongValue());
            }
        }
    }
}
