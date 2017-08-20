package nico.styTool;

import android.content.Context;

import cn.bmob.v3.BmobInstallation;

/**
 * Created by luxin on 15-12-22.
 */
public class MyUserInstallation extends BmobInstallation {

    private String uid;

    public MyUserInstallation(Context context) {
        super(context);
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
