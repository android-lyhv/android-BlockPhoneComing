package halo.com.limitedphone_receviesms_contentporvider.dao;

import java.util.List;

import halo.com.limitedphone_receviesms_contentporvider.bean.Phone;

/**
 * Created by HoVanLy on 7/7/2016.
 */
interface IPhoneDatabase {
    List<Phone> getListPhone();
    boolean addPhone(Phone phone);
    boolean deletePhone(int id_phone);
}
