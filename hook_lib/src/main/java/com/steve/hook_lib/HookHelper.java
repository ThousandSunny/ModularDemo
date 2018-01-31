package com.steve.hook_lib;

import android.content.Context;
import android.os.Handler;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Proxy;

import dalvik.system.DexClassLoader;

/**
 * Created by SteveYan on 2018/1/30.
 */

public class HookHelper {

    public static void hookActivityManagerService(ClassLoader classLoader) {
        try {
            Class<?> activityManagerNativeClass = Class.forName("android.app.ActivityManagerNative");
            if (activityManagerNativeClass == null) {
                return;
            }
            Field gDefaultField = activityManagerNativeClass.getDeclaredField("gDefault");
            if (gDefaultField == null) {
                return;
            }
            gDefaultField.setAccessible(true);
            Object gDefault = gDefaultField.get(null);

            Class<?> singleton = Class.forName("android.util.Singleton");
            if (singleton == null) {
                return;
            }

            Field mInstanceField = singleton.getDeclaredField("mInstance");
            if (mInstanceField == null) {
                return;
            }
            mInstanceField.setAccessible(true);

            Object activityManager = mInstanceField.get(gDefault);
            Class<?> activityManagerInterface = Class.forName("android.app.IActivityManager");
            Object proxy = Proxy.newProxyInstance(classLoader, new Class[]{activityManagerInterface}, new HookActivityHandler(activityManager));
            mInstanceField.set(gDefault, proxy);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 本打算在这也用动态代理，后来发现 ActivityThread 中的 handler 对象的 mCallback 对象是空的；并且 Callback 接口是 public 的，所以直接替换是很好的策略
     */
    public static void hookActivityThreadHandler() {
        try {
            Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
            Field currentActivityThreadField = activityThreadClass.getDeclaredField("sCurrentActivityThread");
            if (currentActivityThreadField == null) {
                return;
            }
            currentActivityThreadField.setAccessible(true);
            Object currentActivityThread = currentActivityThreadField.get(null);

            Field mHField = activityThreadClass.getDeclaredField("mH");
            if (mHField == null) {
                return;
            }
            mHField.setAccessible(true);
            Handler mH = (Handler) mHField.get(currentActivityThread);
            Field mCallbackField = Handler.class.getDeclaredField("mCallback");
            if (mCallbackField == null) {
                return;
            }
            mCallbackField.setAccessible(true);
            mCallbackField.set(mH, new ActivityThreadHandlerCallback(mH));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void loadApk(Context context, String apkPath) {
        File dexFile = context.getDir("dex", Context.MODE_PRIVATE);

        File apkFile = new File(apkPath);

        ClassLoader classLoader = context.getClassLoader();
        DexClassLoader dexClassLoader = new DexClassLoader(apkFile.getAbsolutePath(), dexFile.getAbsolutePath(), null, classLoader.getParent());

        try {
            Field fieldClassLoader = ClassLoader.class.getDeclaredField("parent");
            if (fieldClassLoader != null) {
                fieldClassLoader.setAccessible(true);
                fieldClassLoader.set(classLoader, dexClassLoader);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
