package request.sender.util.factory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EntityClassFactory {
    @Value("${domain.package.name}")
    private String domainPackage;
    public Class<?> getEntityClass(String entityName) {
        try {
            return Class.forName(domainPackage + "." +entityName);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
