package kitkat.auth.mapper;

public interface DtoMapper<T, S> {

    T toDto(S s);
}
