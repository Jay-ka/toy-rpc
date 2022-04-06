package org.manor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.manor.annotation.RpcService;

/**
 * @author kanghuajie
 * @date 2022/3/31
 */
@RpcService
public class HelloServiceImpl implements HelloService{
    @Override
    public String hello() {
        return "this is server say hello";
    }
}
