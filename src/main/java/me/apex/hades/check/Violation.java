package me.apex.hades.check;

public class Violation {
    private final long timeStamp = System.currentTimeMillis();
    private final String information;

    public Violation(String information) {
        this.information = information;
    }

    public String getInformation() {
        return information;
    }

    public long getTimeStamp() {
        return timeStamp;
    }
}
