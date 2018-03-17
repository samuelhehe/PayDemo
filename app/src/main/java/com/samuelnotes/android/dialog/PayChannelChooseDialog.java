package com.samuelnotes.android.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.samuelnotes.android.Constants;
import com.samuelnotes.android.R;


/**
 * author：samuelnotes{@see https://www.samuelnotes.cn} on 16/5/16 16:30
 */
public class PayChannelChooseDialog extends Dialog {


    LinearLayout weixin_llay;

    RadioButton wx_trbtn;

    LinearLayout alipay_llay;

    RadioButton alipay_trbtn;

    Button pay_btn2;

    private OnPopwindowClickListener onPopwindowClickListener;


    public PayChannelChooseDialog(Context context, OnPopwindowClickListener onPopwindowClickListener) {
        super(context, R.style.channel_dialog);
        setOnPopwindowClickListener(onPopwindowClickListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_paychannel_choose);
        getWindow().setGravity(Gravity.BOTTOM);

        WindowManager m = getWindow().getWindowManager();
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams p = getWindow().getAttributes();
        p.width = d.getWidth();
        getWindow().setAttributes(p);
        setCanceledOnTouchOutside(true);

        setOnPopwindowClickListener(onPopwindowClickListener);

        /// 默认微信选中，支付宝未选中
        wx_trbtn = (RadioButton) findViewById(R.id.wx_trbtn);
        alipay_trbtn = (RadioButton) findViewById(R.id.alipay_trbtn);
        weixin_llay = (LinearLayout) findViewById(R.id.weixin_llay);
        alipay_llay = (LinearLayout) findViewById(R.id.alipay_llay);
        pay_btn2 = (Button) findViewById(R.id.pay_btn2);

        wx_trbtn.setChecked(true);
        alipay_trbtn.setChecked(false);

        weixin_llay.setOnClickListener(weixinclick);
        wx_trbtn.setOnClickListener(weixinclick);

        alipay_llay.setOnClickListener(alipayclick);
        alipay_trbtn.setOnClickListener(alipayclick);

        pay_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /// get pay channel
                if (onPopwindowClickListener != null) {
                    onPopwindowClickListener.action(getPayChannel());
                }
                dismiss();
            }
        });


    }


    private int getPayChannel() {
        if (wx_trbtn.isChecked()) {
            return Constants.PAY_WEIXIN;
        } else {
            return Constants.PAY_ALIPAY;
        }
    }


    View.OnClickListener weixinclick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!wx_trbtn.isChecked()) {
                /// 设置选中
                wx_trbtn.setChecked(true);
                alipay_trbtn.setChecked(false);
            }else{
                alipay_trbtn.setChecked(false);
            }
        }
    };

    View.OnClickListener alipayclick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!alipay_trbtn.isChecked()) {
                alipay_trbtn.setChecked(true);
                wx_trbtn.setChecked(false);
            }else{
                wx_trbtn.setChecked(false);
            }

        }
    };


    public interface OnPopwindowClickListener {

        void action(int action);

    }

    public void setOnPopwindowClickListener(OnPopwindowClickListener onPopwindowClickListener) {
        this.onPopwindowClickListener = onPopwindowClickListener;
    }


}
