/*
 * Created by MQuest Pte Ltd on 4/8/21 10:30 PM
 * Copyright (c) MQuest Pte Ltd 2021 . All rights reserved.
 *
 * This library is STRICTLY for MQuest Pte Ltd use only.
 * Govtech agrees not to deploy  part or all of these codes when creating other libraries
 * for internal or external  organizations use.
 *
 * Last modified 4/8/21 9:55 PM
 */

package com.example.lahagorapracticaltask.utils;

import android.util.Log;

public class LogUtils {
    public static void Print(String tag, String message) {
        if (Constants.LOG_TRACE_REQUIRED && message != null && !message.isEmpty()) {
            try {
                int maxLogSize = 1000;
                int start = 0;
                int end = message.length();
                for (int i = 0; i <= message.length() / maxLogSize; i++) {
                    start = i * maxLogSize;
                    end = (i + 1) * maxLogSize;
                    end = Math.min(end, message.length());
                }
                Log.e(tag, message.substring(start, end));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
