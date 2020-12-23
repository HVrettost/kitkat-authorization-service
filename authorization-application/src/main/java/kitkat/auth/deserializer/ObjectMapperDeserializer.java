package kitkat.auth.deserializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kitkat.auth.exception.CoreException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ObjectMapperDeserializer<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ObjectMapperDeserializer.class);

    private final ObjectMapper objectMapper;

    public ObjectMapperDeserializer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public T deserialize(String jsonString, Class<T> clazz) {
        try {
            return objectMapper.readValue(jsonString, clazz);
        } catch (JsonProcessingException ex) {
            LOGGER.error("error processing and mapping json string", ex);
            throw new CoreException(ex.getMessage());
        }

    }
}
