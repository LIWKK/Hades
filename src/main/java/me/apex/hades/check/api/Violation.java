package me.apex.hades.check.api;

public class Violation {


    private long timeStamp;
    private String information;

    public Violation(String information) {
        timeStamp = System.currentTimeMillis();
        this.information = information;
    }

    public String getInformation() {
        return information;
    }

    public long getTimeStamp() {
        return timeStamp;
    }
}
