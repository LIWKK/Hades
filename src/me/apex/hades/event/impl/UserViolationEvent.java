package me.apex.hades.event.impl;

import me.apex.hades.check.Check;
import me.apex.hades.event.Event;

public class UserViolationEvent extends Event {

    private Check check;
    private String information;

    public UserViolationEvent(Check check, String information)
    {
        this.check = check;
        this.information = information;
    }

    public Check getCheck()
    {
        return check;
    }

    public String getInformation()
    {
        return information;
    }

}
