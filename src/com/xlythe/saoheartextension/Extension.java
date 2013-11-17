package com.xlythe.saoheartextension;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

public class Extension {
    public View getView(Context context) {
        ImageView iv = new ImageView(context);
        iv.setImageResource(R.drawable.heart);
        return iv;
    }

    public String getImage() {
        return "icon_heart";
    }

    public void setButtonPos(int[] pos) {}

    public void viewShown() {}

    public void viewHidden() {}
}
