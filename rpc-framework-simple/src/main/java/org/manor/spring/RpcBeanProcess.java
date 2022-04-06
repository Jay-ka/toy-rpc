package org.manor.spring;

import lombok.extern.slf4j.Slf4j;
import org.manor.annotation.RpcReference;
import org.manor.annotation.RpcService;
import org.manor.provider.ServiceProvider;
import org.manor.proxy.ClientProxy;
import org.manor.register.RpcDiscover;
import org.manor.register.RpcRegister;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.InetSocketAddress;

/**
 * @author kanghuajie
 * @date 2022/3/31
 */
@Slf4j
@Component
public class RpcBeanProcess implements BeanPostProcessor {
    @Autowired
    private RpcRegister rpcRegister;

    @Autowired
    private RpcDiscover rpcDiscover;

    @Autowired
    private ClientProxy clientProxy;

    @Autowired
    private ServiceProvider serviceProvider;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        try {
            String hostAddress = InetAddress.getLocalHost().getHostAddress();
            InetSocketAddress address = new InetSocketAddress(hostAddress, 8088);
            Class<?> beanClass = bean.getClass();
            if (beanClass.isAnnotationPresent(RpcService.class)) {
                log.info("检测到rpcService, {}", bean);
                Class<?>[] interfaces = beanClass.getInterfaces();
                for (Class<?> serviceInterface : interfaces) {
                    String serviceName = serviceInterface.getName();
                    rpcRegister.register(serviceName, address);
                    serviceProvider.publishService(serviceName, bean);
                }
            }

            Field[] declaredFields = beanClass.getDeclaredFields();
            for (Field declaredField : declaredFields) {
                if (declaredField.isAnnotationPresent(RpcReference.class)) {
                    declaredField.setAccessible(true);
                    log.info("检测到rpcReference: {}", declaredField);
                    String serviceName = declaredField.getType().getName();
                    InetSocketAddress discover = rpcDiscover.discover(serviceName);
                    Object proxy = clientProxy.getProxy(serviceName, discover);
                    declaredField.set(bean, proxy);
                }
            }
            return bean;
        } catch (Exception e) {
            e.printStackTrace();
            return bean;
        }

    }
}
