package com.zq.android.utils;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Lecion on 11/19/15.
 */
public class StatusBarCompat {
    public static final int INVALID_VALUE = -1;
    private static final int COLOR_DEFAULT = Color.parseColor("#20000000");

    public static void compat(Activity activity, int statusColor) {
        //5.0及以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (statusColor != INVALID_VALUE) {
                activity.getWindow().setStatusBarColor(statusColor);
            }
            return;
        }
        //4.4以上5.0以下，添加一个和statusbar同高的view
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            int color = COLOR_DEFAULT;
            ViewGroup contentView = (ViewGroup) activity.findViewById(android.R.id.content);
            if (color != INVALID_VALUE) {
                color = statusColor;
            }
            View statusBar = new View(activity);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight(activity));
            statusBar.setBackgroundColor(color);
            contentView.addView(statusBar, params);
        }
    }

    private static int getStatusBarHeight(Activity activity) {
        int result = 0;
        int resId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resId > 0) {
            result = activity.getResources().getDimensionPixelSize(resId);
        }
        return result;
    }

    public static void compat(Activity activity) {
        compat(activity, INVALID_VALUE);
    }

}
