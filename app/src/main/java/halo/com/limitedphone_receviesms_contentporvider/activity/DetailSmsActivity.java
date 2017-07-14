package halo.com.limitedphone_receviesms_contentporvider.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import halo.com.limitedphone_receviesms_contentporvider.R;
import halo.com.limitedphone_receviesms_contentporvider.bean.Sms;
import halo.com.limitedphone_receviesms_contentporvider.dao.TimeConvert;

/**
 * Created by HoVanLy on 7/7/2016.
 */
public class DetailSmsActivity extends AppCompatActivity {
    TextView tvContentSms;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_sms);
        tvContentSms = (TextView) findViewById(R.id.tvContentSms);
        Intent intent = getIntent();
        Sms sms = (Sms) intent.getSerializableExtra("sms");
        tvContentSms.append(sms.getPhone_number()+"\n");
        tvContentSms.append(sms.getContent_sms()+"\n");
        if (sms.getTime_receive()!=null){
            tvContentSms.append(TimeConvert.getStringDatetime(sms.getTime_receive()));
        }
    }
}
