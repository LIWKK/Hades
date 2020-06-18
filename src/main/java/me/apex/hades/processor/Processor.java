package me.apex.hades.processor;

import me.apex.hades.user.User;

public abstract class Processor {

    public final User user;

    public Processor(User user) {
        this.user = user;
    }

}
