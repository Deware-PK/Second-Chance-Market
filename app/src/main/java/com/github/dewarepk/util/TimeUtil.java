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

    public static StylishAlertDialog loadDataDialog(Context context, boolean condition, long millis) {
        StylishAlertDialog progressionDialog = new StylishAlertDialog(context, StylishAlertDialog.PROGRESS);
        progressionDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        progressionDialog.setTitleText("Loading")
                .setCancellable(false)
                .setCancelledOnTouchOutside(false);

        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (!condition) {
                    Log.d("Dialog", "Running Task!");
                    if (!progressionDialog.isShowing()) {
                        handler.removeCallbacks(this);
                        progressionDialog.dismissWithAnimation();
                        return;
                    }
                    handler.postDelayed(this, millis);
                } else {
                    handler.removeCallbacks(this);
                    progressionDialog.dismissWithAnimation();
                    Log.d("Dialog", "Finish Loaded!");
                }
            }
        };
        progressionDialog.show();
        handler.post(runnable);
        Log.d("Dialog", "Executed!");

        return progressionDialog;  // Return the dialog for management
    }

}
