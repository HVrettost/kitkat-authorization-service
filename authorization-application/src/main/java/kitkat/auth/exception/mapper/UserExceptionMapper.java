package kitkat.auth.exception.mapper;

import kitkat.auth.deserializer.ObjectMapperDeserializer;
import kitkat.auth.enumeration.ErrorDetails;
import kitkat.auth.exception.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

@Component
public class UserExceptionMapper {

    private final ObjectMapperDeserializer<ErrorDetails> objectMapperDeserializer;

    public UserExceptionMapper(ObjectMapperDeserializer<ErrorDetails> objectMapperDeserializer) {
        this.objectMapperDeserializer = objectMapperDeserializer;
    }

    public AuthenticationException mapToAuthenticationException(HttpClientErrorException ex) {
        ErrorDetails errorDetails = objectMapperDeserializer.deserialize(ex.getResponseBodyAsString(), ErrorDetails.class);
        return new AuthenticationException(errorDetails, ex.getStatusCode());
    }
}
