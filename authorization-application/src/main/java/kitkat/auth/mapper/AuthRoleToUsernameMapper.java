package kitkat.auth.mapper;

import kitkat.auth.model.dto.AuthRoleToUsernameDto;
import kitkat.auth.model.entity.AuthRoleToUsername;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class AuthRoleToUsernameMapper implements DtoMapper<AuthRoleToUsernameDto, AuthRoleToUsername>, EntityMapper<AuthRoleToUsername, AuthRoleToUsernameDto> {

    private final ModelMapper modelMapper;

    public AuthRoleToUsernameMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public AuthRoleToUsernameDto toDto(AuthRoleToUsername authRoleToUsername) {
        return modelMapper.map(authRoleToUsername, AuthRoleToUsernameDto.class);
    }

    @Override
    public AuthRoleToUsername toEntity(AuthRoleToUsernameDto authRoleToUsernameDto) {
        return modelMapper.map(authRoleToUsernameDto, AuthRoleToUsername.class);
    }
}
