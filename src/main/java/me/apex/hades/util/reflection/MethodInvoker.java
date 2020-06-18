package me.apex.hades.util.reflection;

public interface MethodInvoker {
    public Object invoke(Object target, Object... arguments);
}
