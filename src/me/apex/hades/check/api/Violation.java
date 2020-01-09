package me.apex.hades.check.api;

import lombok.Getter;

public class Violation {

    @Getter
    private long timeStamp;
    @Getter
    private String information;

    public Violation(String information)
    {
        timeStamp = System.currentTimeMillis();
        this.information = information;
    }

}
