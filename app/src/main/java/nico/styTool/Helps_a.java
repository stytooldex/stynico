package nico.styTool;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

/**
 * Created by luxin on 15-12-8.
 *  http://luxin.gitcafe.io
 */
public class Helps_a extends BmobObject implements Serializable {
   // private static final long serialVersionUID=1L;

    private String content;
    private String pushdate;
    private int state;
    private MyUser user;
    private MyUser hepluser;
    private HelpInfo helpInfo;
    
    private boolean phontofile;


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPushdate() {
        return pushdate;
    }

    public void setPushdate(String pushdate) {
        this.pushdate = pushdate;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public MyUser getUser() {
        return user;
    }

    public void setUser(MyUser user) {
        this.user = user;
    }

    public MyUser getHepluser() {
        return hepluser;
    }

    public void setHepluser(MyUser hepluser) {
        this.hepluser = hepluser;
    }

    public HelpInfo getHelpInfo() {
        return helpInfo;
    }

    public void setHelpInfo(HelpInfo helpInfo) {
        this.helpInfo = helpInfo;
    }

    public boolean getPhontofile() {
        return phontofile;
    }

    public void setPhontofile(boolean phontofile) {
        this.phontofile = phontofile;
    }



}
