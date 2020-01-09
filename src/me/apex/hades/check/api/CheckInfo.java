package me.apex.hades.check.api;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface CheckInfo {
    public String Name();
    public Check.CheckType Type();
    public boolean Experimental();
}
