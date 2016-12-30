package com.sunstar.vegnet.kootl.comm.base;

/**
 * Created by louisgeek on 2016/12/4.
 */

public interface BaseModel<T> {
    void loadData(BaseCallBack<T> baseCallBack);
}