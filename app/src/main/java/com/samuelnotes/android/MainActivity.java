package com.samuelnotes.android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.samuelnotes.android.alipay.PayDemoActivity;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.weixinpay_btn).setOnClickListener(this);
        findViewById(R.id.alipay_btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.weixinpay_btn:
//                Toast.makeText(this, "launching weipay  ", Toast.LENGTH_LONG).show();

                startActivity(new Intent(this, PayActivity.class));
                break;

            case R.id.alipay_btn:
                startActivity(new Intent(this, PayDemoActivity.class));

                break;

        }
    }
}
