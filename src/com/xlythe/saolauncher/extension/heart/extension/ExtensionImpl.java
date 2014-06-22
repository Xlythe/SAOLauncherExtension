package com.xlythe.saolauncher.extension.heart.extension;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.xlythe.saolauncher.extension.heart.theme.Theme;

class ExtensionImpl implements ExtensionInterface {
    private Object mObj;

    ExtensionImpl(Object obj) {
        mObj = obj;
    }

    @Override
    public void setTheme(String packageName, Typeface typeface) {
        setTheme(mObj, packageName, typeface);
    }

    @Override
    public void setSide(String side) {
        setSide(mObj, side);
    }

    @Override
    public View getView(Context context) {
        return getView(mObj, context);
    }

    @Override
    public String getImage() {
        return getImage(mObj);
    }

    @Override
    public String getInactiveImage() {
        return getInactiveImage(mObj);
    }

    @Override
    public void setButtonPos(int[] pos) {
        setButtonPos(mObj, pos);
    }

    @Override
    public void onResume() {
        onResume(mObj);
    }

    @Override
    public void onPause() {
        onPause(mObj);
    }

    private static void setTheme(Object extension, String packageName, Typeface typeface) {
        try {
            Class<?> viewExtractor = extension.getClass();

            Method m = viewExtractor.getDeclaredMethod("setTheme", String.class, Typeface.class);
            m.invoke(extension, packageName, typeface);
        }
        catch(IllegalArgumentException e) {
            e.printStackTrace();
        }
        catch(IllegalAccessException e) {
            e.printStackTrace();
        }
        catch(InvocationTargetException e) {
            e.printStackTrace();
        }
        catch(NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    private static void setSide(Object extension, String side) {
        try {
            Class<?> viewExtractor = extension.getClass();

            Method m = viewExtractor.getDeclaredMethod("setSide", String.class);
            m.invoke(extension, side);
        }
        catch(IllegalArgumentException e) {
            e.printStackTrace();
        }
        catch(IllegalAccessException e) {
            e.printStackTrace();
        }
        catch(InvocationTargetException e) {
            e.printStackTrace();
        }
        catch(NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    private static View getView(Object extension, Context context) {
        try {
            Class<?> viewExtractor = extension.getClass();

            Method m = viewExtractor.getDeclaredMethod("getView", Context.class);
            return (View) m.invoke(extension, context);
        }
        catch(IllegalArgumentException e) {
            e.printStackTrace();
        }
        catch(IllegalAccessException e) {
            e.printStackTrace();
        }
        catch(InvocationTargetException e) {
            e.printStackTrace();
        }
        catch(NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getImage(Object extension) {
        try {
            Class<?> viewExtractor = extension.getClass();

            Method m = viewExtractor.getDeclaredMethod("getImage");
            return (String) m.invoke(extension);
        }
        catch(IllegalArgumentException e) {
            e.printStackTrace();
        }
        catch(IllegalAccessException e) {
            e.printStackTrace();
        }
        catch(InvocationTargetException e) {
            e.printStackTrace();
        }
        catch(NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getInactiveImage(Object extension) {
        try {
            Class<?> viewExtractor = extension.getClass();

            Method m = viewExtractor.getDeclaredMethod("getImage");
            return (String) m.invoke(extension);
        }
        catch(IllegalArgumentException e) {
            e.printStackTrace();
        }
        catch(IllegalAccessException e) {
            e.printStackTrace();
        }
        catch(InvocationTargetException e) {
            e.printStackTrace();
        }
        catch(NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void onResume(Object extension) {
        try {
            Class<?> viewExtractor = extension.getClass();

            Method m = viewExtractor.getDeclaredMethod("onResume");
            m.invoke(extension);
        }
        catch(IllegalArgumentException e) {
            e.printStackTrace();
        }
        catch(IllegalAccessException e) {
            e.printStackTrace();
        }
        catch(InvocationTargetException e) {
            e.printStackTrace();
        }
        catch(NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    private static void onPause(Object extension) {
        try {
            Class<?> viewExtractor = extension.getClass();

            Method m = viewExtractor.getDeclaredMethod("onPause");
            m.invoke(extension);
        }
        catch(IllegalArgumentException e) {
            e.printStackTrace();
        }
        catch(IllegalAccessException e) {
            e.printStackTrace();
        }
        catch(InvocationTargetException e) {
            e.printStackTrace();
        }
        catch(NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    private static void setButtonPos(Object extension, int[] pos) {
        try {
            Class<?> viewExtractor = extension.getClass();

            Method m = viewExtractor.getDeclaredMethod("setButtonPos", int[].class);
            m.invoke(extension, pos);
        }
        catch(IllegalArgumentException e) {
            e.printStackTrace();
        }
        catch(IllegalAccessException e) {
            e.printStackTrace();
        }
        catch(InvocationTargetException e) {
            e.printStackTrace();
        }
        catch(NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    /**
     * Grabs the Resources from packageName
     * */
    private static Resources getResources(Context context, String extension) {
        try {
            return context.getPackageManager().getResourcesForApplication(extension);
        }
        catch(NameNotFoundException e) {
            e.printStackTrace();
            return context.getResources();
        }
    }

    private static Drawable getDrawable(Context context, String extension, String name) {
        int id = Theme.getId(context, Theme.DRAWABLE, name);
        if(id != 0) {
            return Theme.getResources(context).getDrawable(id);
        }

        // No drawable in the theme, grab from extension
        id = getResources(context, extension).getIdentifier(name, Theme.DRAWABLE, extension);
        if(id != 0) {
            return getResources(context, extension).getDrawable(id);
        }

        // No drawable in the extension, grab from app
        id = context.getResources().getIdentifier(name, Theme.DRAWABLE, context.getPackageName());
        if(id != 0) {
            return context.getResources().getDrawable(id);
        }

        return null;
    }
}
