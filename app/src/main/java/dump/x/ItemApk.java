package dump.x;

import android.graphics.drawable.Drawable;

import java.util.Comparator;

public class ItemApk {
    private Drawable dIcon;
    private String dataDir;
    private String displayName;
    private String packageName;
    private boolean running;
    private int size;
    private String sourceDir;
    private int versionCode;
    private String versionName;

    public static class DisplayNameAscendComparator implements Comparator<ItemApk> {
        public int compare(ItemApk apk1, ItemApk apk2) {
            return apk1.getDisplayName().compareTo(apk2.getDisplayName());
        }
    }

    public static class DisplayNameAscendComparatorIC implements Comparator<ItemApk> {
        public int compare(ItemApk apk1, ItemApk apk2) {
            return apk1.getDisplayName().compareToIgnoreCase(apk2.getDisplayName());
        }
    }

    public static class DisplayNameDescendComparator implements Comparator<ItemApk> {
        public int compare(ItemApk apk1, ItemApk apk2) {
            return -apk1.getDisplayName().compareTo(apk2.getDisplayName());
        }
    }

    public static class DisplayNameDescendComparatorIC implements Comparator<ItemApk> {
        public int compare(ItemApk apk1, ItemApk apk2) {
            return -apk1.getDisplayName().compareToIgnoreCase(apk2.getDisplayName());
        }
    }

    public static class SizeAscendComparator implements Comparator<ItemApk> {
        public int compare(ItemApk apk1, ItemApk apk2) {
            int diff = apk1.getSize() - apk2.getSize();
            if (diff < 0) {
                return -1;
            }
            if (diff > 0) {
                return 1;
            }
            return 0;
        }
    }

    public static class SizeDescendComparator implements Comparator<ItemApk> {
        public int compare(ItemApk apk1, ItemApk apk2) {
            return new SizeAscendComparator().compare(apk2, apk1);
        }
    }

    public String getPackageName() {
        return this.packageName;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public String getSourceDir() {
        return this.sourceDir;
    }

    public String getDataDir() {
        return this.dataDir;
    }

    public String getVersionName() {
        return this.versionName;
    }

    public int getVersionCode() {
        return this.versionCode;
    }

    public Drawable getIcon() {
        return this.dIcon;
    }

    public int getSize() {
        return this.size;
    }

    public boolean getRunning() {
        return this.running;
    }

    public String getStrSize() {
        if (this.size > 1048576) {
            return new StringBuilder(String.valueOf(String.valueOf(this.size / 1048576))).append(" MB").toString();
        }
        if (this.size > 1024) {
            return new StringBuilder(String.valueOf(String.valueOf(this.size / 1024))).append(" KB").toString();
        }
        return new StringBuilder(String.valueOf(String.valueOf(this.size / 1024))).append(" B").toString();
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setSourceDir(String sourceDir) {
        this.sourceDir = sourceDir;
    }

    public void setDataDir(String dataDir) {
        this.dataDir = dataDir;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public void setIcon(Drawable dIcon) {
        this.dIcon = dIcon;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
