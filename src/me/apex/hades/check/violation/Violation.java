package me.apex.hades.check.violation;

public class Violation {

    private long timeStamp;
    private String information;

    public Violation(String information) { timeStamp = System.currentTimeMillis(); this.information = information; }

    public long getTimeStamp() { return timeStamp; }
    public String getInformation() { return information; }

}
