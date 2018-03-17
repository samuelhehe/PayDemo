package com.samuelnotes.android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.samuelnotes.android.alipay.PayDemoActivity;
import com.samuelnotes.android.dialog.PayChannelChooseDialog;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private PayChannelChooseDialog payChannelChooseDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.pay_btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pay_btn:
                payChannelChooseDialog = new PayChannelChooseDialog(this,
                        new PayChannelChooseDialog.OnPopwindowClickListener() {
                            @Override
                            public void action(int action) {
                                switch (action) {

                                    case Constants.PAY_ALIPAY:
                                        startActivity(new Intent(MainActivity.this, PayActivity.class));
                                        break;
                                    case Constants.PAY_WEIXIN:

                                        startActivity(new Intent(MainActivity.this, PayDemoActivity.class));
                                        break;

                                }


                            }
                        });
                payChannelChooseDialog.show();


                break;

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (payChannelChooseDialog != null) {
            payChannelChooseDialog.dismiss();
        }
    }
}
