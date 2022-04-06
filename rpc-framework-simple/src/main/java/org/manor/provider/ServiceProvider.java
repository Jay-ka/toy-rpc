package org.manor.provider;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author kanghuajie
 * @date 2022/4/6
 */
@Component
public class ServiceProvider {
    private Map<String, Object> services = new HashMap<>();

    public void publishService(String serviceName, Object service) {
        this.services.put(serviceName, service);
    }

    public Object getServiceByServiceName(String serviceName) {
        return this.services.get(serviceName);
    }
}
