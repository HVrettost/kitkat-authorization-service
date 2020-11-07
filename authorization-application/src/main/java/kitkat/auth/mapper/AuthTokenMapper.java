package kitkat.auth.mapper;

import kitkat.auth.model.dto.AuthTokenDto;
import kitkat.auth.model.entity.AuthToken;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class AuthTokenMapper implements DtoMapper<AuthTokenDto, AuthToken>, EntityMapper<AuthToken, AuthTokenDto> {

    private final ModelMapper modelMapper;

    public AuthTokenMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public AuthTokenDto toDto(AuthToken authToken) {
        return modelMapper.map(authToken, AuthTokenDto.class);
    }

    @Override
    public AuthToken toEntity(AuthTokenDto authTokenDto) {
        return modelMapper.map(authTokenDto, AuthToken.class);
    }
}
