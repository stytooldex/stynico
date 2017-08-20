package nico.styTool;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.List;

import cn.bmob.v3.BmobInstallation;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;


public class RegisterActivity extends Fragment {
    private String webviewi = "apktool1";
    private EditText ediusername;
    private EditText ediemail;
    private EditText edipassword;
    private EditText edipasswordTwo;
    private Button btnReg;

    private void qqq() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        AlertDialog alert = builder.setMessage("注册失败：之前有帐号直接登录即可\n\n忘记了密码：直接找回密码\n\n登录失败：注册帐号即可\n\n不懂可直接加入官方群使用通用帐号")
                .setNegativeButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setCancelable(false).create();
        alert.show();
    }

    public static String getHostIP() {

        String hostIp = null;
        try {
            Enumeration nis = NetworkInterface.getNetworkInterfaces();
            InetAddress ia = null;
            while (nis.hasMoreElements()) {
                NetworkInterface ni = (NetworkInterface) nis.nextElement();
                Enumeration<InetAddress> ias = ni.getInetAddresses();
                while (ias.hasMoreElements()) {
                    ia = ias.nextElement();
                    if (ia instanceof Inet6Address) {
                        continue;// skip ipv6
                    }
                    String ip = ia.getHostAddress();
                    if (!"127.0.0.1".equals(ip)) {
                        hostIp = ia.getHostAddress();
                        break;
                    }
                }
            }
        } catch (SocketException e) {
            // Log.i("yao", "SocketException");
            e.printStackTrace();
        }
        return hostIp;

    }

    private void register() {
        String username = ediusername.getText().toString().trim();
        String email = ediemail.getText().toString().trim();
        String password = edipassword.getText().toString().trim();
        String passwordTwo = edipasswordTwo.getText().toString().trim();
        ediusername.setError(null);
        ediemail.setError(null);
        edipassword.setError(null);
        edipasswordTwo.setError(null);
        if (TextUtils.isEmpty(username)) {
            ediusername.setError("用户名不能为空");
            return;
        }
        if (TextUtils.isEmpty(email)) {
            ediemail.setError("请输入邮箱");
            return;
        }
        if (!isEmailValidate(email)) {
            ediemail.setError("这不是一个有效的邮箱");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            edipassword.setError("密码不能为空");
            return;
        }
        if (TextUtils.isEmpty(passwordTwo)) {
            edipasswordTwo.setError("请再次输入一次密码");
            return;
        }

        if (!password.equals(passwordTwo)) {
            new AlertDialog.Builder(getActivity()).setMessage("两次输入的密码不一致").create().show();
            return;
        }

        if (!isPasswordValidate(password)) {
            new AlertDialog.Builder(getActivity()).setMessage("密码长度过短，最小7位以上").create().show();
            return;
        }

        TelephonyManager tm = (TelephonyManager) getActivity().getSystemService(getActivity().TELEPHONY_SERVICE);
        final MyUser user = new MyUser();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setScore(10);
        user.setSex(1);
		user.setAddress("不激活");
        user.setHol(tm.getDeviceId());
        user.setid(getHostIP());
        user.signUp(getActivity(), new SaveListener() {
            @Override
            public void onSuccess() {

                bindUserIdAndDriverice(user);
                Toast.makeText(getActivity(), "注册成功", Toast.LENGTH_SHORT).show();
                getActivity().sendBroadcast(new Intent(Constant.ACTION_REGISTER_SUCCESS_FINISH));

            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(getActivity(), "注册失败\n日志:"+i, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 将用户与设备绑定起来
     *
     * @param user
     */
    private void bindUserIdAndDriverice(final MyUser user) {
        BmobQuery<MyUserInstallation> query = new BmobQuery<MyUserInstallation>();
        query.addWhereEqualTo("installationId", BmobInstallation.getInstallationId(getActivity()));
        query.findObjects(getActivity(), new FindListener<MyUserInstallation>() {
            @Override
            public void onSuccess(List<MyUserInstallation> list) {
                if (list.size() > 0) {
                    MyUserInstallation myUserInstallation = list.get(0);
                    myUserInstallation.setUid(user.getObjectId());
                    myUserInstallation.update(getActivity(), new UpdateListener() {
                        @Override
                        public void onSuccess() {
                            getActivity().finish();
                        }

                        @Override
                        public void onFailure(int i, String s) {
                        }
                    });
                }
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lxw_register, null);
        SharedPreferences setting = getActivity().getSharedPreferences(webviewi, 0);
        Boolean user_first = setting.getBoolean("FIRST", true);
        if (user_first) {
            //setting.edit().putBoolean("FIRST", false).commit();
            qqq();
        } else {


        }
        ediusername = (EditText) view.findViewById(R.id.lxw_id_reg_edi_username);
        ediemail = (EditText) view.findViewById(R.id.lxw_id_reg_edi_email);
        edipassword = (EditText) view.findViewById(R.id.lxw_id_reg_edi_password);
        edipasswordTwo = (EditText) view.findViewById(R.id.lxw_id_reg_edi_password_two);
        btnReg = (Button) view.findViewById(R.id.lxw_id_reg_btn_register);
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });

        return view;

    }


    private boolean isPasswordValidate(String password) {
        return password.length() > 6;
    }

    private boolean isEmailValidate(String email) {
        return email.contains("@");
    }
}
