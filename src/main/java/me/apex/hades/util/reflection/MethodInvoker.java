package me.apex.hades.util.reflection;

public interface MethodInvoker {
    Object invoke(Object target, Object... arguments);
}
