package org.manor;

import org.manor.annotation.RpcScan;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author kanghuajie
 * @date 2022/4/6
 */
@RpcScan
public class ClientMain {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ClientMain.class);
        MyService bean = context.getBean(MyService.class);
        bean.test();
    }
}
