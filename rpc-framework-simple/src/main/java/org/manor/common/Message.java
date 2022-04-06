package org.manor.common;

import lombok.Data;

/**
 * @author kanghuajie
 * @date 2022/4/6
 */
@Data
public class Message {
    private String requestId;

    private String serviceName;

    private String methodName;

    private Object[] args;
}
