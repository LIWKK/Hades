package me.apex.hades.util.reflection;

public interface FieldAccessor<T> {
    T get(Object target);

    void set(Object target, Object value);

    boolean hasField(Object target);
}
