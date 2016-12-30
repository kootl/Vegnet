package com.sunstar.vegnet.kootl.comm.helper;

import android.app.Activity;
import android.content.Intent;

import com.sunstar.vegnet.custom.KooApplication;
import com.sunstar.vegnet.kootl.comm.config.CommFinalData;
import com.sunstar.vegnet.kootl.comm.tool.SharedPreferencesTool;

/**
 * Created by louisgeek on 2016/12/23.
 */

public class DayNightHelper {

    public static boolean isNight() {
        boolean isNight = (boolean) SharedPreferencesTool.get(KooApplication.getAppContext(), CommFinalData.SP_IS_NIGHT, false);
        return isNight;
    }

    public static void refreshActivity(Activity activity) {
        Intent intent = activity.getIntent();
        activity.overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        activity.finish();
        activity.overridePendingTransition(0, 0);
        activity.startActivity(intent);
    }

}
