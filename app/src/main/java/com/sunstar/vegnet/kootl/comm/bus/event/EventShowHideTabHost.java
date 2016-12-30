package com.sunstar.vegnet.kootl.comm.bus.event;

/**
 * Created by louisgeek on 2016/12/14.
 */

public class EventShowHideTabHost {
    public EventShowHideTabHost(boolean isShow) {
        this.isShow = isShow;
    }

    public boolean isShow() {
        return isShow;

    }

    public void setShow(boolean show) {
        isShow = show;
    }

    private boolean isShow;
}
