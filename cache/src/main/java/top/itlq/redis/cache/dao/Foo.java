package top.itlq.redis.cache.dao;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@ToString
public class Foo {
    private Long id;
    private String name;
    private Integer amount;
    private BigDecimal price;
    private Instant createTime;
    private Instant operTime;
}
