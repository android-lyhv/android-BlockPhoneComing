package halo.com.limitedphone_receviesms_contentporvider.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by HoVanLy on 7/7/2016.
 */
public class Sms implements Serializable{
    int id_sms;
    String phone_number;
    String content_sms;
    Date time_receive;

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getContent_sms() {
        return content_sms;
    }

    public void setContent_sms(String content_sms) {
        this.content_sms = content_sms;
    }

    public Date getTime_receive() {
        return time_receive;
    }

    public void setTime_receive(Date time_receive) {
        this.time_receive = time_receive;
    }

    public int getId_sms() {
        return id_sms;
    }

    public void setId_sms(int id_sms) {
        this.id_sms = id_sms;
    }
}

