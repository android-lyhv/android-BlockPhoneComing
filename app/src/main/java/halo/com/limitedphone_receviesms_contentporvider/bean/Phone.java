package halo.com.limitedphone_receviesms_contentporvider.bean;

import java.io.Serializable;

/**
 * Created by HoVanLy on 7/7/2016.
 */
public class Phone implements Serializable {
    int id_phone;
    String phone_number;

    public int getId_phone() {
        return id_phone;
    }

    public void setId_phone(int id_phone) {
        this.id_phone = id_phone;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

}
