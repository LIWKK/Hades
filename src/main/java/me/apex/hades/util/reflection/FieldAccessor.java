package me.apex.hades.util.reflection;

public interface FieldAccessor<T> {
    public T get(Object target);
    public void set(Object target, Object value);
    public boolean hasField(Object target);
}
