package me.apex.hades.util.reflection.reflections.types;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
public class WrappedConstructor {
    private final WrappedClass parent;
    private Constructor constructor;

    public <T> T newInstance(Object... args) {
        try {
            constructor.setAccessible(true);
            return (T) constructor.newInstance(args);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }

    public <T> T newInstance() {
        try {
            constructor.setAccessible(true);
            return (T) constructor.newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }
}
