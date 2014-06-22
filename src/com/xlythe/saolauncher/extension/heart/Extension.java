package com.xlythe.saolauncher.extension.heart;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

public class Extension extends ExtensionTemplate {
    @Override
    protected View inflateView(Context context) {
        ImageView iv = new ImageView(context);
        iv.setImageResource(R.drawable.heart);
        return iv;
    }

    @Override
    public String getImage() {
        return "icon_heart";
    }

    @Override
    public String getInactiveImage() {
        return "icon_heart";
    }
}
