package com.sunstar.vegnet.kootl.ssqselect.bean;

/**
 * Created by louisgeek on 2016/12/23.
 */

public class AreaDataWrapper {

    private String name;
    private String pinYin;

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    private String areaId;
    private boolean isShowMoreIcon;

    public int getNowShowWhere() {
        return nowShowWhere;
    }

    public void setNowShowWhere(int nowShowWhere) {
        this.nowShowWhere = nowShowWhere;
    }

    private int nowShowWhere;


    public int getHoldSelectedProvincePos() {
        return holdSelectedProvincePos;
    }

    public void setHoldSelectedProvincePos(int holdSelectedProvincePos) {
        this.holdSelectedProvincePos = holdSelectedProvincePos;
    }

    public int getHoldSelectedCityPos() {
        return holdSelectedCityPos;
    }

    public void setHoldSelectedCityPos(int holdSelectedCityPos) {
        this.holdSelectedCityPos = holdSelectedCityPos;
    }

    private int holdSelectedProvincePos;
    private int holdSelectedCityPos;

    public int getHoldSelectedAreaPos() {
        return holdSelectedAreaPos;
    }

    public void setHoldSelectedAreaPos(int holdSelectedAreaPos) {
        this.holdSelectedAreaPos = holdSelectedAreaPos;
    }

    private int holdSelectedAreaPos;


    public String getPinYin() {
        return pinYin;
    }

    public void setPinYin(String pinYin) {
        this.pinYin = pinYin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isShowMoreIcon() {
        return isShowMoreIcon;
    }

    public void setShowMoreIcon(boolean showMoreIcon) {
        isShowMoreIcon = showMoreIcon;
    }


}
