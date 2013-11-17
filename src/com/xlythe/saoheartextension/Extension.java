package com.xlythe.saoheartextension;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

public class Extension {
    public static View getView(Context context) {
        ImageView iv = new ImageView(context);
        iv.setImageResource(R.drawable.heart);
        return iv;
    }

    public static String getImage() {
        return "icon_heart";
    }
}
