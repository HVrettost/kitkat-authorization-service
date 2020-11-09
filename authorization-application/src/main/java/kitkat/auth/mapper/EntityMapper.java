package kitkat.auth.mapper;

public interface EntityMapper<T, S> {

    T toEntity(S s);
}
