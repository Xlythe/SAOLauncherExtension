package com.xlythe.saolauncher.extension.heart.extension;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;

public interface ExtensionInterface {
    /**
     * Called only once per session. Passes the package name of the enabled theme.
     * */
    public void setTheme(String packageName, Typeface typeface);

    /**
     * Called only once per session. Passes the side (left or right) that the launcher is set as.
     * */
    public void setSide(String side);

    /**
     * Called only once per session. Create the view you wish to show. The view will be given match_parent for both width and height so it's recommended that you encapsulate your view in a FrameLayout to avoid filling the screen.
     * */
    public View getView(Context context);

    /**
     * Return the orb's image. This will first attempt to grab the image from the installed theme before defaulting to the image in the extension
     * */
    public String getImage();

    /**
     * Return the orb's inactive image. This will first attempt to grab the image from the installed theme before defaulting to the image in the extension
     * */
    public String getInactiveImage();

    /**
     * Gives you the center cords of the orb This will be called every time before onResume (the orb is in a ScrollView so, if you want to stay centered, adjust here)
     * */
    public void setButtonPos(int[] pos);

    /**
     * Your view is now visible
     * */
    public void onResume();

    /**
     * Your view has been hidden
     * */
    public void onPause();
}
