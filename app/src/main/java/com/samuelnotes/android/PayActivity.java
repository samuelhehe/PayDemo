package com.samuelnotes.android;


import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.samuelnotes.android.utils.Util;
import com.tencent.mm.opensdk.constants.Build;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class PayActivity extends Activity {

    private IWXAPI api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay);

        api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);
        api.registerApp(Constants.APP_ID);


        Button appayBtn = (Button) findViewById(R.id.appay_btn);
        appayBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                task.execute();
            }
        });
        Button checkPayBtn = (Button) findViewById(R.id.check_pay_btn);
        checkPayBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                boolean isPaySupported = api.getWXAppSupportAPI() >= Build.PAY_SUPPORTED_SDK_INT;
                Toast.makeText(PayActivity.this, String.valueOf(isPaySupported), Toast.LENGTH_SHORT).show();
            }
        });

    }

    AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Button payBtn = (Button) findViewById(R.id.appay_btn);
            payBtn.setEnabled(false);
            Toast.makeText(PayActivity.this, "获取订单中...", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected String doInBackground(Void... params) {

            String url = "http://wxpay.wxutil.com/pub_v2/app/app_pay.php";


            byte[] buf = Util.httpGet(url);
            if (buf != null && buf.length > 0) {
                String content = new String(buf);
                return content;
            }

            return "";
        }

        @Override
        protected void onPostExecute(String content) {
            super.onPostExecute(content);

            try {
                Log.e("get server pay params:", content);
                JSONObject json = new JSONObject(content);
                if (null != json && !json.has("retcode")) {
                    PayReq req = new PayReq();
                    req.appId = Constants.APP_ID;  // 测试用appId
//                    req.appId = json.getString("appid");
//                    req.partnerId = json.getString("partnerid");

                    req.prepayId = json.getString("prepayid");

                    req.nonceStr = json.getString("noncestr");
                    req.timeStamp = json.getString("timestamp");
                    req.packageValue = json.getString("package");
                    req.sign = json.getString("sign");
//                    req.sign = "008653ce7eb42e364a43fb9ad65a43fb";
                    req.extData = "app data"; // optional
                    Toast.makeText(PayActivity.this, "正常调起支付", Toast.LENGTH_SHORT).show();
                    // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
                    api.sendReq(req);
                } else {
                    Log.d("PAY_GET", "返回错误" + json.getString("retmsg"));
                    Toast.makeText(PayActivity.this, "返回错误" + json.getString("retmsg"), Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                Log.e("PAY_GET", "异常：" + e.getMessage());
                Toast.makeText(PayActivity.this, "异常：" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }
    };


}
