package org.manor;

import org.manor.annotation.RpcReference;
import org.springframework.stereotype.Component;

/**
 * @author kanghuajie
 * @date 2022/4/6
 */
@Component
public class MyService {
    @RpcReference
    private HelloService helloService;

    public void test() {
        String hello = helloService.hello();
        System.out.println(hello);
    }
}
