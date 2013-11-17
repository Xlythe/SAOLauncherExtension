package com.xlythe.saoheartextension;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

public class Extension implements ExtensionInterface {
    @Override
    public View getView(Context context) {
        ImageView iv = new ImageView(context);
        iv.setImageResource(R.drawable.heart);
        return iv;
    }

    @Override
    public String getImage() {
        return "icon_heart";
    }

    @Override
    public void setButtonPos(int[] pos) {}

    @Override
    public void onResume() {}

    @Override
    public void onPause() {}
}
