package com.mcourse.utils;

import android.content.Context;
import android.os.Build;
import android.os.PowerManager;
import android.os.SystemClock;
import android.util.Log;

import java.lang.reflect.Method;

/**
 * Created by YUANZQ2 on 2018-4-3.
 * 电源工具类：关屏、wakeLock
 */

public class PowerUtil {
    private static final String TAG = PowerUtil.class.getSimpleName();
    private static PowerManager.WakeLock mWorkLock;

    //判断系统是否还处在可交互状态（还没有开始进入休眠阶段）
    private static boolean isInteractive = true;
    private final static Object paramLock = new Object();

    private static PowerManager.WakeLock mOtherLock;        //下载、安装等其它情况下的锁

    /**
     * 获取电源锁，保持该服务在屏幕熄灭时仍然获取CPU时，保持运行(用于主屏中的熄屏按钮)
     */
    public static void acquireWakeLock(Context context) {
        if (null == mWorkLock) {
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            mWorkLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK | PowerManager.ON_AFTER_RELEASE, "FULL_WAKE_LOCK");
            if (null != mWorkLock) {
                Log.i(TAG, "call acquireWakeLock");
                mWorkLock.acquire();
            }
        }
    }

    // 释放设备电源锁
    public static void releaseWakeLock() {
//        Log.i(TAG, "mWorkLock = " + (mWorkLock == null ? "null" : "not null")
//                + "; isHeld = " + (mWorkLock == null ? "false" : mWorkLock.isHeld())
//        );

        if (null != mWorkLock && mWorkLock.isHeld()) {
            Log.i(TAG, "releaseWakeLock");
            mWorkLock.release();
            mWorkLock = null;
        }
    }


    /**
     * 获取电源锁，保持该服务在屏幕熄灭时仍然获取CPU时，保持运行(用于主屏中的熄屏按钮)
     */
    public static void acquireOtherLock(Context context) {
        if (null == mOtherLock) {
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            mOtherLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK | PowerManager.ON_AFTER_RELEASE, "FULL_WAKE_LOCK");
            if (null != mOtherLock) {
                Log.i(TAG, "call acquireOtherLock");
                mOtherLock.acquire();
            }
        }
    }

    // 释放设备电源锁
    public static void releaseOtherLock() {
        if (null != mOtherLock && mOtherLock.isHeld()) {
            Log.i(TAG, "releaseOtherLock");
            mOtherLock.release();
            mOtherLock = null;
        }
    }


    /**
     * 亮屏
     */
    public static void turnOnscreen(Context context) {
//        if (isScreenOn(context)) return;
        if (isIsInteractive()) {
            Log.e(TAG, "isInteractive = true");
            return;
        }
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "unlock");
        wakeLock.acquire();
        wakeLock.release();
    }

    /**
     * 熄屏
     */
    public static void turnOffscreen(Context context) {
//        if (!isInteractive) return;
        if (!isIsInteractive()) return;
        Log.e(TAG, "<---- turnOffscreen ---->");
        try {
            Class c = Class.forName("android.os.PowerManager");
            PowerManager mPowerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            for (Method m : c.getDeclaredMethods()) {
                if (m.getName().equals("goToSleep")) {
                    m.setAccessible(true);
                    if (m.getParameterTypes().length == 1) {
                        m.invoke(mPowerManager, SystemClock.uptimeMillis());
                    }
                }
            }
            setIsInteractive(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    /**
//     * 熄屏
//     */
//    public static void turnOffscreen(Context context) {
//        LogUtil.e(TAG, "<---- turnOffscreen ---->");
//        if (!isScreenOn(context)) return;
//        isInteractive = false;
//        try {
//            Class c = Class.forName("android.os.PowerManager");
//            PowerManager mPowerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
//            for (Method m : c.getDeclaredMethods()) {
//                if (m.getName().equals("goToSleep")) {
//                    m.setAccessible(true);
//                    if (m.getParameterTypes().length == 1) {
//                        m.invoke(mPowerManager, SystemClock.uptimeMillis());
//                    }
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    /**
     * Returns true if the device is in an interactive state
     */
    public static boolean isScreenOn(Context context) {
        PowerManager powerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        if (powerManager == null) {
            Log.i(TAG, "powerManager == null");
            return false;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
            return powerManager.isInteractive();    //Returns true if the device is in an interactive state
        } else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT_WATCH) {
            return powerManager.isScreenOn();       //Returns true if the device is in an interactive state.
        }

        Log.i(TAG, "---------> return false");
        return false;
    }

    public static boolean isIsInteractive() {
        synchronized (paramLock) {
            return isInteractive;
        }
    }

    public static void setIsInteractive(boolean isInteractive) {
        synchronized (paramLock) {
            PowerUtil.isInteractive = isInteractive;
        }
    }

    //CPU浅度睡眠锁
    private static PowerManager.WakeLock lock;

    public static void acquireCPUAliveLock(Context context) {
        try {
            if (lock != null) {
                releaseCPUAliveLock();
            }
            PowerManager pm = (PowerManager) context.getApplicationContext().getSystemService(Context.POWER_SERVICE);
            if (pm != null) {
                lock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "ScreenOff");
                lock.acquire();
            }
//            LogUtil.e(TAG, "acquireCPUAliveLock");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void releaseCPUAliveLock() {
        try {
            if (lock != null && lock.isHeld()) {
                lock.release();
                lock = null;
            }
//            LogUtil.e(TAG, "releaseCPUAliveLock");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Wifi重连锁
    private static PowerManager.WakeLock wifiReConnectLock;

    public static void acquireWifiReConnectLock(Context context) {
        try {
            if (wifiReConnectLock != null) {
                releaseWifiReConnectLock();
            }
            PowerManager pm = (PowerManager) context.getApplicationContext().getSystemService(Context.POWER_SERVICE);
            if (pm != null) {
                wifiReConnectLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "WifiReConnectLock");
                wifiReConnectLock.acquire();
            }
            Log.e(TAG, "acquireWifiReConnectLock");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void releaseWifiReConnectLock() {
        try {
            if (wifiReConnectLock != null && wifiReConnectLock.isHeld()) {
                wifiReConnectLock.release();
                wifiReConnectLock = null;
            }
            Log.e(TAG, "releaseWifiReConnectLock");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
