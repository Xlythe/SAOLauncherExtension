package com.xlythe.saoheartextension;

import android.content.Context;
import android.view.View;

public interface ExtensionInterface {
    /**
     * Called only once per session. Create the view you wish to show. The view
     * will be given match_parent for both width and height so it's recommended
     * that you encapsulate your view in a FrameLayout to avoid filling the
     * screen.
     * */
    public View getView(Context context);

    /**
     * Return the orb's image. This will first attempt to grab the image from
     * the installed theme before defaulting to the image in the extension
     * */
    public String getImage();

    /**
     * Gives you the center cords of the orb This will be called every time
     * before onResume (the orb is in a ScrollView so, if you want to stay
     * centered, adjust here)
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
