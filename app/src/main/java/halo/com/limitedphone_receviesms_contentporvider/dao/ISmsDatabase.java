package halo.com.limitedphone_receviesms_contentporvider.dao;


import java.util.List;

import halo.com.limitedphone_receviesms_contentporvider.bean.Sms;

/**
 * Created by HoVanLy on 6/20/2016.
 */
interface ISmsDatabase {
    List<Sms> getListsSms();

    boolean addSms(Sms sms);

    boolean deleteSms(int id_sms);

}
