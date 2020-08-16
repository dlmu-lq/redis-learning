package top.itlq.redis.cache.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author liqiang
 * @description
 * @date 2020/8/16 下午5:45
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BarEntity implements Serializable {

    private static final long serialVersionUID = -1912894568704012003L;

    private Long id;
    private String name;
}
