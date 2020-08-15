package top.itlq.redis.base.order;

import io.lettuce.core.KeyValue;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.sync.RedisCommands;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;

public class OrderServiceTest {

    // redis连接后的同步命令实例
    static RedisCommands<String, String> redisCommands;
    // 测试使用的用户
    static Long userId;

    static {
        RedisURI redisURI = RedisURI.builder()
                .withHost("localhost")
                .withPort(6379)
                .build();
        RedisClient redisClient = RedisClient.create(redisURI);
        redisCommands = redisClient.connect().sync();
        userId = newUserId();
    }

    /**
     * 初始时生成多个订单
     */
    @Test
    void generateOrders(){
//        Long orderId1 = OrderServiceTest.newOrderId(),
//                orderId2 = OrderServiceTest.newOrderId(),
//                orderId3 = OrderServiceTest.newOrderId();
        Long orderId1 = 1L,
                orderId2 = 2L,
                orderId3 = 3L;
        redisCommands.hmset("order:" + orderId1, new HashMap<String, String>(){{
            put("orderId", orderId1.toString());
            put("money", "36.6");
            put("time", "2020-04-24");
        }});
        redisCommands.hmset("order:" + orderId2, new HashMap<String, String>(){{
            put("orderId", orderId2.toString());
            put("money", "36.6");
            put("time", "2020-04-24");
        }});
        redisCommands.hmset("order:" + orderId3, new HashMap<String, String>(){{
            put("orderId", orderId3.toString());
            put("money", "36.6");
            put("time", "2020-04-24");
        }});
    }

    /**
     * 将初始订单绑定到订单用户表
     */
    @Test
    void bindOrdersToUserOrder(){
        String redisUserOrderKey = "user:" + userId + ":order";
        redisCommands.lpush(redisUserOrderKey, "order:1", "order:2", "order:3");
    }

    /**
     * 新增一个订单
     */
    @Test
    void addOrder(){
        Long newOrderId = 4L;
        redisCommands.hmset("order:" + newOrderId, new HashMap<String, String>(){{
            put("orderId", newOrderId.toString());
            put("money", "36.6");
            put("time", "2020-04-24");
        }});
    }

    /**
     * 将新订单加入到用户订单表
     */
    @Test
    void bindNewOrderToUserOrder(){
        redisCommands.lpush("user:"+ userId + ":order", "order:4");
    }

    /**
     * 查询所有该用户订单
     */
    @Test
    void queryUserOrders(){
        List<String> orderKeys = redisCommands.lrange("user:" + userId + ":order", 0, -1);
        System.out.println();
        for(String orderKey:orderKeys){
            System.out.print(orderKey + " ");
            List<KeyValue<String,String>> keyValues = redisCommands.hmget(orderKey, "orderId", "money", "time");
            for (KeyValue<String, String> keyValue:keyValues){
                System.out.print(keyValue.getValue() + " ");
            }
            System.out.println();
        }
    }

    /**
     * 新增订单主键
     * @return
     */
//    private static Long newOrderId(){
//        return redisCommands.incr("order");
//    }

    /**
     * 新增用户主键
     * @return
     */
    private static Long newUserId(){
        return redisCommands.incr("user");
    }
}
