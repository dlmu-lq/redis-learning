package top.itlq.redis.base.hash;

import lombok.Data;

import java.time.Instant;
import java.time.LocalDateTime;

/**
 * @author liqiang
 * @description 实体
 * @date 2020/8/15 4:14 下午
 */
@Data
public class User {

    private Long id;

    private String name;

    private LocalDateTime birth;

    private Instant createTime;
}
