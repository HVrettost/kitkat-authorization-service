package kitkat.auth.validator;

public interface Validator<T> {

    void validate(T t);
}
