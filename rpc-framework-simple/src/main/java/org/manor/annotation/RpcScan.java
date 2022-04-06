package org.manor.annotation;

import org.manor.spring.ScannerRegister;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(ScannerRegister.class)
public @interface RpcScan {
    String[] basePackages() default {};
}
