package request.sender.util;

import lombok.RequiredArgsConstructor;
import org.springframework.web.client.RestTemplate;
import request.sender.util.factory.EntityClassFactory;

import java.util.function.Supplier;

@RequiredArgsConstructor
public class RequestSender implements Supplier<Object> {
    private final RestTemplate restTemplate;
    private final String endpoint;
    private final String entityName;
    private final EntityClassFactory entityClassFactory;

    @Override
    public Object get() {
        var clazz = entityClassFactory.getEntityClass(entityName);
        System.out.println("Sending request to :" + endpoint);
        return restTemplate.getForObject(endpoint, clazz);
    }
}
