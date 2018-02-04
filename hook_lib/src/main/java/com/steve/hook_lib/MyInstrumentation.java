package com.steve.hook_lib;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Bundle;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by steveyan on 2/4/18.
 * Email:tinggengyan@gmail.com
 */

public class MyInstrumentation extends Instrumentation {

    private Context mContext;
    private String pluginPath;

    public MyInstrumentation(Instrumentation mInstrumentation, Context context, String pluginPath) {
        super();
        this.mContext = context;
        this.pluginPath = pluginPath;
    }

    @Override
    public void callActivityOnCreate(Activity activity, Bundle icicle) {

        try {

            Field mBaseField = Activity.class.getSuperclass().getSuperclass().getDeclaredField("mBase");
            mBaseField.setAccessible(true);
            Context mBase = (Context) mBaseField.get(activity);

            Class<?> contextImplClazz = Class.forName("android.app.ContextImpl");
            Field mResourcesField = contextImplClazz.getDeclaredField("mResources");
            mResourcesField.setAccessible(true);

            String dexPath = activity.getApplicationContext().getPackageResourcePath();

            AssetManager assetManager = AssetManager.class.newInstance();
            Method addAssetPath = assetManager.getClass().getMethod("addAssetPath", String.class);

            addAssetPath.setAccessible(true);
            addAssetPath.invoke(assetManager, dexPath);
            addAssetPath.invoke(assetManager, pluginPath);

            Method ensureStringBlocksMethod = AssetManager.class.getDeclaredMethod("ensureStringBlocks");
            ensureStringBlocksMethod.setAccessible(true);
            ensureStringBlocksMethod.invoke(assetManager);

            Resources superRes = activity.getResources();
            Resources resources = new Resources(assetManager, superRes.getDisplayMetrics(), superRes.getConfiguration());

            mResourcesField.set(mBase, resources);

        } catch (Exception e) {
            e.printStackTrace();
        }

        super.callActivityOnCreate(activity, icicle);

    }
}
