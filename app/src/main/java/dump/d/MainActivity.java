package dump.d;

import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import dump.g.GsonUtil;
import dump.g.OkHttpUtils;
import dump.g.StringCallback;
import nico.styTool.R;
import nico.styTool.StatusBarUtil;
import okhttp3.Call;



public class MainActivity extends AppCompatActivity
{
	android.support.v7.widget.Toolbar toolbar;

    private EditText editText;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);

        initView();
    }

    private void initView()
	{
        setContentView(R.layout.activity_bain);}}
