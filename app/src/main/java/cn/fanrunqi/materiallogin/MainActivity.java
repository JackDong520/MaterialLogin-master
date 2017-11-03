package cn.fanrunqi.materiallogin;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.transition.Explode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.fanrunqi.materiallogin.http.OkHttp;
import cn.fanrunqi.materiallogin.javabeans.UserEntity;

public class MainActivity extends AppCompatActivity {

    @InjectView(R.id.et_username)
    EditText etUsername;
    @InjectView(R.id.et_password)
    EditText etPassword;
    @InjectView(R.id.bt_go)
    Button btGo;
    @InjectView(R.id.cv)
    CardView cv;
    @InjectView(R.id.fab)
    FloatingActionButton fab;
    private Handler myHandler;
    private EditText userEdit;
    private EditText pwdEdit;
    private OkHttp okHttp = new OkHttp();
    private String url = "http://192.168.1.104:8080/login";
    private int code = 0;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        getSupportActionBar().hide();

        myHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 200:
                        if (msg.getData().get("registerCode").equals("0")) {
                            System.out.println("0000");

                            Toast.makeText(MainActivity.this, "登陆失败", Toast.LENGTH_SHORT).show();

                            etUsername.setText("");
                            etPassword.setText("");
                        }
                        if (msg.getData().get("registerCode").equals("1")) {
                            System.out.println("1");
                            Explode explode = new Explode();
                            explode.setDuration(500);
                            getWindow().setExitTransition(explode);
                            getWindow().setEnterTransition(explode);
                            ActivityOptionsCompat oc2 = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this);
                            Intent i2 = new Intent(MainActivity.this,LoginSuccessActivity.class);
                            startActivity(i2, oc2.toBundle());
                            Toast.makeText(MainActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
                            //startActivity(new Intent(LonginActivity.this, WordSearchActivity.class));
                        }
                        break;
                    default:
                        break;
                }
                super.handleMessage(msg);
            }
        };
    }

    @OnClick({R.id.bt_go, R.id.fab})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab:
                getWindow().setExitTransition(null);
                getWindow().setEnterTransition(null);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options =
                            ActivityOptions.makeSceneTransitionAnimation(this, fab, fab.getTransitionName());
                    startActivity(new Intent(this, RegisterActivity.class), options.toBundle());
                } else {
                    startActivity(new Intent(this, RegisterActivity.class));
                }
                break;


            case R.id.bt_go:

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String registerCode = null;
                        try {

                            registerCode = okHttp.loginRequest(url, new UserEntity(0,
                                    etUsername.getText().toString(), etPassword.getText().toString()));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("registerCode", registerCode);
                        Message message = new Message();
                        message.setData(bundle);
                        message.what = 200;
                        myHandler.sendMessage(message);
                        System.out.println(etUsername.getText().toString()+etPassword.getText().toString());
                    }
                }).start();

                break;
        }
    }
}
