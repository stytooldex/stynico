package nico.styTool;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import cn.bmob.v3.BmobInstallation;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.UpdateListener;

//import android.support.v7.app.AppCompatActivity;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends Fragment
{

    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    //private View mLoginFormView;

    private MyBroadcastReceiver myBroadcastReceiver = new MyBroadcastReceiver();

    private void attemptLogin()
    {
        mEmailView.setError(null);
        mPasswordView.setError(null);
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (!TextUtils.isEmpty(password) && !isPasswordValid(password))
		{
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        if (TextUtils.isEmpty(email))
		{
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        }
		else if (!isEmailValid(email))
		{
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel)
		{
            focusView.requestFocus();
        }
		else
		{
            lxwLogin(email, password);
            // showProgress(true,email,password);
        }
    }

    private boolean isEmailValid(String email)
    {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password)
    {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }


    private void lxwLogin(String email, String password)
    {
        MyUser user = new MyUser();
        user.setEmail(email);
        user.setPassword(password);
        BmobUser.loginByAccount(getActivity(), email, password, new LogInListener<MyUser>() {
				@Override
				public void done(MyUser myUser, BmobException e)
				{
					if (myUser != null)
					{
						//Toast.makeText(getActivity(), "用户登录成功", Toast.LENGTH_SHORT).show();
						bindUserIdAndDriverice(myUser);
					}
					else
					{
						mProgressView.setVisibility(View.GONE);
						//mLoginFormView.setVisibility(View.VISIBLE);
						AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
						builder.setMessage("登录失败，可能是邮箱或者密码错误!").create().show();

						//Log.e(TAG, "---login_error----" + e);
					}
				}
			});
    }

    /**
     * 将用户与设备绑定起来
     *
     * @param user
     */
    private void bindUserIdAndDriverice(final MyUser user)
    {
		//  Log.e(TAG, "===bindUserIdAndDriverce==");

        BmobQuery<MyUserInstallation> query = new BmobQuery<MyUserInstallation>();
        query.addWhereEqualTo("installationId", BmobInstallation.getInstallationId(getActivity()));
        query.findObjects(getActivity(), new FindListener<MyUserInstallation>() {
				@Override
				public void onSuccess(List<MyUserInstallation> list)
				{

					//   */ Log.e(TAG,"==list=size==="+list.size());
					if (list.size() > 0)
					{
						//     Log.e(TAG, "===list===: " + list.get(0).toString());
						MyUserInstallation myUserInstallation = list.get(0);
						myUserInstallation.setUid(user.getObjectId());
						myUserInstallation.update(getActivity(), new UpdateListener() {
								@Override
								public void onSuccess()
								{
									//     Log.e(TAG, "===设备信息更新成功===");
									getActivity().finish();
								}

								@Override
								public void onFailure(int i, String s)
								{
									//    Log.e(TAG, "===设备信息更新失败:" + s);
								}
							});
					}
				}

				@Override
				public void onError(int i, String s)
				{
					// Log.e(TAG, "===find erro==" + s);
				}
			});
    }


    private interface ProfileQuery
    {
        String[] PROJECTION = {ContactsContract.CommonDataKinds.Email.ADDRESS,ContactsContract.CommonDataKinds.Email.IS_PRIMARY,};

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // TODO: Implement this method
        View view=inflater.inflate(R.layout.activity_login, null);

        mEmailView = (AutoCompleteTextView) view.findViewById(R.id.email);
        //populateAutoComplete();

        mPasswordView = (EditText) view.findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
				@Override
				public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent)
				{
					if (id == R.id.login || id == EditorInfo.IME_NULL)
					{
						attemptLogin();
						return true;
					}
					return false;
				}
			});

        Button mEmailSignInButton = (Button) view.findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View view)
				{
					attemptLogin();
				}
			});

        //mLoginFormView = view.findViewById(R.id.login_form);
        mProgressView = view.findViewById(R.id.login_progress);

        //注册退出广播
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.ACTION_REGISTER_SUCCESS_FINISH);
        getActivity().registerReceiver(myBroadcastReceiver, filter);

        return view;
	}
    private class MyBroadcastReceiver extends BroadcastReceiver
    {

        @Override
        public void onReceive(Context context, Intent intent)
		{
            if (intent != null && intent.getAction().equals(Constant.ACTION_REGISTER_SUCCESS_FINISH))
			{
                getActivity().finish();
            }
        }}}
