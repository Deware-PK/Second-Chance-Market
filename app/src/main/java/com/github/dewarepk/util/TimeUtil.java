package com.github.dewarepk.util;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.marsad.stylishdialogs.StylishAlertDialog;

import java.util.function.Predicate;


public final class TimeUtil {

    private static Handler handler;
    private static Runnable runnable;

    public static void delayExecution(long delayMillis, Runnable task) {
        new Handler(Looper.getMainLooper()).postDelayed(task, delayMillis);
    }

    public static void loadDataDialog(Context context, boolean condition, long millis) {
        StylishAlertDialog progressionDialog = new StylishAlertDialog(context, StylishAlertDialog.PROGRESS);
        progressionDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        progressionDialog.setTitleText("Loading")
                .setCancellable(false)
                .setCancelledOnTouchOutside(false)
                .show();

        // สร้าง Handler และ Runnable เพื่อใช้ในการตรวจสอบเงื่อนไข
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                // ตรวจสอบเงื่อนไข
                if (!condition) {
                    Log.d("Dialog", "Loading!");
                    // เรียก Runnable อีกครั้งหลังจาก 1 วินาที
                    handler.postDelayed(this, millis);
                } else {
                    // เมื่อเงื่อนไขเป็นจริงให้หยุด Runnable และปิด dialog
                    handler.removeCallbacks(runnable);
                    progressionDialog.dismissWithAnimation();
                    Log.d("Dialog", "Finish Loaded!");
                }
            }
        };

        // เริ่ม Runnable ครั้งแรก
        handler.post(runnable);
    }

    public static void stopTask() {
        handler.removeCallbacks(runnable);
    }
}
