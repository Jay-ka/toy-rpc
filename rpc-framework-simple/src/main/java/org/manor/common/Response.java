package org.manor.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author kanghuajie
 * @date 2022/4/6
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Response {
    private String requestId;
    private Integer code;
    private Object message;
}
