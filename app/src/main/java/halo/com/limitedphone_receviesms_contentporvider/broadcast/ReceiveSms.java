package halo.com.limitedphone_receviesms_contentporvider.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

import halo.com.limitedphone_receviesms_contentporvider.bean.Sms;
import halo.com.limitedphone_receviesms_contentporvider.dao.SmsDatabase;


/**
 * Created by HoVanLy on 7/7/2016.
 */
public class ReceiveSms extends BroadcastReceiver {
    Bundle bundle;
    SmsDatabase smsDatabase;


    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
            bundle = intent.getExtras();
            String content_sms = getContentSms(intent);
            String phone_number = getPhoneSend(intent);
            Date time_receive=getTime(intent);
            addSms(context, content_sms, phone_number, time_receive);
            Toast.makeText(context, "New SMS: " + content_sms, Toast.LENGTH_LONG).show();
            Log.e("New Sms", content_sms);
           // deleteMessage(context);
        }

    }

    private String getContentSms(Intent intent) {
        // Nhan cac tin nhan gui den cung luc
        Object[] objectContent = (Object[]) bundle.get("pdus");
        String content = "";
        for (int i = 0; i < objectContent.length; i++) {
            SmsMessage smsMsg = SmsMessage.createFromPdu((byte[]) objectContent[i]);
            content = smsMsg.getMessageBody();
        }
        return content;
    }

    private String getPhoneSend(Intent intent) {
        Object[] objectContent = (Object[]) bundle.get("pdus");
        String phoneNumber = "";
        for (int i = 0; i < objectContent.length; i++) {
            SmsMessage smsMsg = SmsMessage.createFromPdu((byte[]) objectContent[i]);
            phoneNumber = smsMsg.getDisplayOriginatingAddress();
        }
        return phoneNumber;
    }

    private Date getTime(Intent intent) {
        Object[] objectContent = (Object[]) bundle.get("pdus");
        Date date = null;
        for (int i = 0; i < objectContent.length; i++) {
            SmsMessage smsMsg = SmsMessage.createFromPdu((byte[]) objectContent[i]);
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(smsMsg.getTimestampMillis());
            date = calendar.getTime();
        }
        return date;
    }

    private void addSms(Context context, String content_sms, String phone_number, Date date) {
        smsDatabase = new SmsDatabase(context);
        Sms sms = new Sms();
        sms.setContent_sms(content_sms);
        sms.setPhone_number(phone_number);
        sms.setTime_receive(date);
        smsDatabase.addSms(sms);
        smsDatabase.close();
    }
    private int deleteMessage(Context context) {
        Uri deleteUri = Uri.parse("content://sms");
        int count = 0;
        Cursor c = context.getContentResolver().query(deleteUri, null, null,
                null, null);
        while (c.moveToNext()) {
            try {
                // Delete the SMS
                String pid = c.getString(0); // Get id;
                String uri = "content://sms/" + pid;
                count = context.getContentResolver().delete(Uri.parse(uri),
                        null, null);
            } catch (Exception e) {
            }
        }
        return count;
    }
}
