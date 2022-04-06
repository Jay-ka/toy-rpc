package org.manor.annotation;

import java.lang.annotation.*;

/**
 * @author kanghuajie
 * @date 2022/4/6
 */

@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RpcReference {

}
