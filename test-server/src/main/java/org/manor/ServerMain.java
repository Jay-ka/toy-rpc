package org.manor;

import org.manor.annotation.RpcScan;
import org.manor.remoting.server.RpcServer;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author kanghuajie
 * @date 2022/3/31
 */
@RpcScan(basePackages = {"org.manor"})
public class ServerMain {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ServerMain.class);
        RpcServer server = (RpcServer) context.getBean("server");
        server.start();
    }
}
