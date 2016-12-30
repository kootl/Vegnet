package com.sunstar.vegnet.kootl.comm.tool;

import java.util.Collection;

/**
 * Created by louisgeek on 2016/11/2.
 */

public class CollectionTool {

    public static boolean isNullOrEmpty(Collection collection) {
        if (collection == null || collection.isEmpty()) {
            return true;
        }
        return false;
    }
}
