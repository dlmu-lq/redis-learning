package top.itlq.redis.cache.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@SpringBootTest
@Slf4j
class FooServiceTest {
    @Autowired(required = false)
    FooService fooService;

    @Test
    void testCache() throws InterruptedException {
        final int THREAD_SIZE = 100;
        long fooId = 31L;
        CountDownLatch countDownLatch = new CountDownLatch(1);
        for(int i=0;i<THREAD_SIZE;i++){
            new Thread(()->{
                try {
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info(fooService.getFoo(fooId).toString());
//                log.info(fooService.getFoo2(fooId).toString());
//                log.info(fooService.getFooFromDB(fooId).toString());
            }).start();
        }
        TimeUnit.SECONDS.sleep(1);
        countDownLatch.countDown();
        // spring boot test 主线程已结束，之前创建的线程马上结束
        TimeUnit.SECONDS.sleep(5);
    }
}