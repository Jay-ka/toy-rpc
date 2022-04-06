package org.manor.spring;

import lombok.extern.slf4j.Slf4j;
import org.manor.annotation.RpcService;
import org.manor.serializer.RpcSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.StandardAnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author kanghuajie
 * @date 2022/3/31
 */
@Slf4j
public class ScannerRegister implements ImportBeanDefinitionRegistrar {
    private static final String BEAN_SCAN_PACKAGE = "org.mannor";

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        Map<String, Object> annotationAttributes = importingClassMetadata.getAnnotationAttributes("org.manor.annotation.RpcScan");
        String[] rpcServicePackages = (String[]) annotationAttributes.get("basePackages");
        if (rpcServicePackages.length == 0) {
            rpcServicePackages = new String[] {((StandardAnnotationMetadata)importingClassMetadata).getIntrospectedClass().getPackage().getName()};
        }


        ClassPathBeanDefinitionScanner rpcServiceScanner = new ClassPathBeanDefinitionScanner(registry);
        rpcServiceScanner.addIncludeFilter(new AnnotationTypeFilter(RpcService.class));
        int rpcServices = rpcServiceScanner.scan(rpcServicePackages);
        log.info("扫描到{}个rpcService", rpcServices);

        ClassPathBeanDefinitionScanner beanScanner = new ClassPathBeanDefinitionScanner(registry);
        beanScanner.addIncludeFilter(new AnnotationTypeFilter(Component.class));
        int beanCount = beanScanner.scan(BEAN_SCAN_PACKAGE);
        log.info("扫描到{}个bean", beanCount);

        //启动server

    }
}
