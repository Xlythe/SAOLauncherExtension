package com.xlythe.saolauncher.extension.heart.extension;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.content.res.AssetFileDescriptor;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import com.xlythe.saolauncher.extension.heart.theme.App;
import com.xlythe.saolauncher.extension.heart.theme.Theme;

import dalvik.system.DexClassLoader;

public class Extension {
    /**
     * Returns a list of installed apps that are registered as extensions
     * */
    public static List<App> getApps(Context context) {
        LinkedList<App> apps = new LinkedList<App>();
        PackageManager manager = context.getPackageManager();

        Intent mainIntent = new Intent(context.getPackageName() + ".EXTENSION", null);

        final List<ResolveInfo> infos;
        try {
            infos = manager.queryIntentActivities(mainIntent, 0);
        }
        catch(Exception e) {
            e.printStackTrace();
            return apps;
        }

        for(ResolveInfo info : infos) {
            App app = new App();
            apps.add(app);

            app.setName(info.loadLabel(manager).toString());
            app.setPackageName(info.activityInfo.applicationInfo.packageName);
        }
        return apps;
    }

    public static ExtensionInterface getExtension(Context context, String packageName) {
        try {
            // Use cursor loader to grab dex file
            Uri uri = Uri.parse("content://" + packageName + ".FileProvider/" + "classes.dex");
            AssetFileDescriptor a = context.getContentResolver().openAssetFileDescriptor(uri, null);
            FileInputStream in = new FileInputStream(a.getFileDescriptor());
            in.skip(a.getStartOffset());
            File file = new File(context.getCacheDir(), packageName + ".classes.dex");
            file.createNewFile();
            FileOutputStream fOutput = new FileOutputStream(file);
            byte[] dataBuffer = new byte[1024];
            int readLength = 0;
            while((readLength = in.read(dataBuffer)) != -1) {
                fOutput.write(dataBuffer, 0, readLength);
            }
            in.close();
            fOutput.close();

            // Open dex
            final String libPath = file.getAbsolutePath();
            final DexClassLoader classloader = new DexClassLoader(libPath, file.getAbsolutePath(), null, context.getClassLoader());
            final Class classToLoad = Class.forName(packageName + ".Extension");
            final Object myInstance = classToLoad.newInstance();
            final Method onResume = classToLoad.getMethod("onResume");
            onResume.invoke(myInstance);

        }
        catch(Exception e) {
            // Do nothing
            e.printStackTrace();
        }
        return getLegacyExtension(context, packageName);
    }

    public static ExtensionInterface getLegacyExtension(Context context, String packageName) {
        try {
            Context packageContext = context.createPackageContext(packageName, Context.CONTEXT_INCLUDE_CODE + Context.CONTEXT_IGNORE_SECURITY);
            ClassLoader loader = packageContext.getClassLoader();
            Class<?> viewExtractor = loader.loadClass(packageName + ".Extension");

            return new ExtensionImpl(viewExtractor.newInstance());
        }
        catch(IllegalAccessException e) {
            e.printStackTrace();
        }
        catch(NameNotFoundException e) {
            e.printStackTrace();
        }
        catch(ClassNotFoundException e) {
            e.printStackTrace();
        }
        catch(InstantiationException e) {
            e.printStackTrace();
        }

        return null;
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

    public static Drawable getDrawable(Context context, String extension, String name) {
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

    public static Context createContext(Context context, String packageName) {
        // Create context for app
        try {
            return context.createPackageContext(packageName, Context.CONTEXT_INCLUDE_CODE + Context.CONTEXT_IGNORE_SECURITY);
        }
        catch(NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
