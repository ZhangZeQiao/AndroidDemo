package com.zzq.demo.util;

import android.os.Handler;
import android.os.HandlerThread;

public class WorkThreadHandler {

    private static volatile Handler sWorkThreadHandler;

    private WorkThreadHandler() {
    }

    public static Handler getWorkThreadHandler() {
        if (sWorkThreadHandler == null) {
            synchronized (WorkThreadHandler.class) {
                if (sWorkThreadHandler == null) {
                    HandlerThread handlerThread = new HandlerThread("com.zzq.HandlerThread");
                    handlerThread.start();
                    sWorkThreadHandler = new Handler(handlerThread.getLooper());
                }
            }
        }
        return sWorkThreadHandler;
    }

    public static void runInWorkThreadDelay(Runnable runnable, long delay) {
        if (runnable == null) {
            return;
        }
        getWorkThreadHandler().postDelayed(runnable, delay);
    }

    public static void removeWorkThreadRunnable(Runnable runnable) {
        if (runnable == null) {
            return;
        }
        getWorkThreadHandler().removeCallbacks(runnable);
    }

}
