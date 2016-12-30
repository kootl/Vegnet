package com.sunstar.vegnet.kootl.comm.bus.event;

/**
 * Created by louisgeek on 2016/12/27.
 */

public class EventAreaSelected {

    private String AreaID;

    public String getAreaID() {
        return AreaID;
    }

    public String getAreaName() {
        return AreaName;
    }

    public EventAreaSelected(String areaID, String areaName) {
        AreaID = areaID;

        AreaName = areaName;
    }

    private String AreaName;

}
