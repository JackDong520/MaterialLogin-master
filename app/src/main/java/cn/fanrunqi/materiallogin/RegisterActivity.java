package cn.fanrunqi.materiallogin;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.junit.runners.model.InitializationError;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.fanrunqi.materiallogin.http.OkHttp;
import cn.fanrunqi.materiallogin.javabeans.UserEntity;

public class RegisterActivity extends AppCompatActivity {

    @InjectView(R.id.fab)
    FloatingActionButton fab;
    @InjectView(R.id.cv_add)
    CardView cvAdd;

    private EditText etUsername;
    private EditText etPassword;

    private Button btGo;
    private Handler myHandler;
    private OkHttp okHttp = new OkHttp();
    private String url = "http://192.168.1.104:8080/register";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.inject(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ShowEnterAnimation();
        }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateRevealClose();
            }
        });

        myHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 200:
                        if (msg.getData().get("registerCode").equals("0")) {
                            etUsername.setText("");
                            etPassword.setText("");
                            Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                        }
                        if (msg.getData().get("registerCode").equals("1"))
                        {
                            Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                        }
                        break;
                    default:
                        break;
                }
                super.handleMessage(msg);
            }
        };
        btGo = (Button) findViewById(R.id.bt_going);
        etUsername = (EditText) findViewById(R.id.et_usernaming);
        etPassword = (EditText) findViewById(R.id.et_passwording);
        initView();
    }


    private void ShowEnterAnimation() {
        Transition transition = TransitionInflater.from(this).inflateTransition(R.transition.fabtransition);
        getWindow().setSharedElementEnterTransition(transition);

        transition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {
                cvAdd.setVisibility(View.GONE);
            }

            @Override
            public void onTransitionEnd(Transition transition) {
                transition.removeListener(this);
                animateRevealShow();
            }

            @Override
            public void onTransitionCancel(Transition transition) {

            }

            @Override
            public void onTransitionPause(Transition transition) {

            }

            @Override
            public void onTransitionResume(Transition transition) {

            }


        });
    }

    public void animateRevealShow() {
        Animator mAnimator = ViewAnimationUtils.createCircularReveal(cvAdd, cvAdd.getWidth()/2,0, fab.getWidth() / 2, cvAdd.getHeight());
        mAnimator.setDuration(500);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                cvAdd.setVisibility(View.VISIBLE);
                super.onAnimationStart(animation);
            }
        });
        mAnimator.start();
    }

    public void animateRevealClose() {
        Animator mAnimator = ViewAnimationUtils.createCircularReveal(cvAdd,cvAdd.getWidth()/2,0, cvAdd.getHeight(), fab.getWidth() / 2);
        mAnimator.setDuration(500);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                cvAdd.setVisibility(View.INVISIBLE);
                super.onAnimationEnd(animation);
                fab.setImageResource(R.drawable.plus);
                RegisterActivity.super.onBackPressed();
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }
        });
        mAnimator.start();
    }
    @Override
    public void onBackPressed() {
        animateRevealClose();
    }

//    @OnClick({R.id.bt_go})
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.bt_go:
//                System.out.println("0");
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        try {
//                            String registerCode = okHttp.registerRequest(url, new UserEntity(0,
//                                    etUsername.getText().toString(), etPassword.getText().toString()));
//                            Bundle bundle = new Bundle();
//                            bundle.putSerializable("registerCode", registerCode);
//                            Message message = new Message();
//                            message.setData(bundle);
//                            message.what = 200;
//                            myHandler.sendMessage(message);
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }).start();
//                break;
//        }
//    }
    private void initView() {
        btGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String registerCode = okHttp.registerRequest(url, new UserEntity(0,
                                    etUsername.getText().toString(), etPassword.getText().toString()));
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("registerCode", registerCode);
                            Message message = new Message();
                            message.setData(bundle);
                            message.what = 200;
                            myHandler.sendMessage(message);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
    }

}
