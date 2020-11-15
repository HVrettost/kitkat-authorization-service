package kitkat.auth.mapper;

import kitkat.auth.model.dto.AuthRoleToAuthoritiesDto;
import kitkat.auth.model.entity.AuthRoleToAuthorities;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class AuthRoleToAuthoritiesMapper implements DtoMapper<AuthRoleToAuthoritiesDto, AuthRoleToAuthorities>,
        EntityMapper<AuthRoleToAuthorities, AuthRoleToAuthoritiesDto> {

    private final ModelMapper modelMapper;

    public AuthRoleToAuthoritiesMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public AuthRoleToAuthoritiesDto toDto(AuthRoleToAuthorities authRoleToAuthorities) {
        return modelMapper.map(authRoleToAuthorities, AuthRoleToAuthoritiesDto.class);
    }

    @Override
    public AuthRoleToAuthorities toEntity(AuthRoleToAuthoritiesDto authRoleToAuthoritiesDto) {
        return modelMapper.map(authRoleToAuthoritiesDto, AuthRoleToAuthorities.class);
    }
}
