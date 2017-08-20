package dump.a;

import java.io.DataOutputStream;
import java.io.OutputStream;

/**
 * Created by tpnet on 2016/5/19.
 */
public class RootManage {

    /**
     * 判断手机是否root成功
     * @return 有权限返回真，没权限返回假
     * */
    public static boolean isRoot() {
        int i = execRootCmdSilent("echo test"); // 通过执行测试命令来检测
        return i == 0;
    }
    /**
     * 执行命令判断有没有权限
     * @param paramString 要执行的命令
     * */
    private static int execRootCmdSilent(String paramString) {
        try {
            Process localProcess = Runtime.getRuntime().exec("su");
            Object localObject = localProcess.getOutputStream();
            DataOutputStream localDataOutputStream = new DataOutputStream((OutputStream) localObject);
            String str = String.valueOf(paramString);
            localObject = str + "\n";
            localDataOutputStream.writeBytes((String) localObject);
            localDataOutputStream.flush();
            localDataOutputStream.writeBytes("exit\n");
            localDataOutputStream.flush();
            localProcess.waitFor();
            int result = localProcess.exitValue();
            return result;
        } catch (Exception localException) {
            localException.printStackTrace();
            return -1;
        }
    }
}
