package halo.com.limitedphone_receviesms_contentporvider.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import halo.com.limitedphone_receviesms_contentporvider.bean.Sms;


/**
 * Created by HoVanLy on 6/21/2016.
 */
public class SmsDatabase extends AbstractDataBaseHelper implements ISmsDatabase {
    /**
     * Constructor
     * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
     *
     * @param context
     */
    private SQLiteDatabase mySqlDatabase;

    public SmsDatabase(Context context) {
        super(context);
        this.mySqlDatabase = getSQLiteDatabase();
    }


    @Override
    public List<Sms> getListsSms() {
        List<Sms> smsList = new ArrayList<>();
        String sqlQuery = "Select * from sms";
        Cursor cursor = mySqlDatabase.rawQuery(sqlQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Sms sms = new Sms();
                sms.setId_sms(cursor.getInt(cursor.getColumnIndex("id_sms")));
                sms.setPhone_number(cursor.getString(cursor.getColumnIndex("phone_number")));
                sms.setContent_sms(cursor.getString(cursor.getColumnIndex("content_sms")));
                if (cursor.getString(cursor.getColumnIndex("time_receive")) != null) {
                    sms.setTime_receive(TimeConvert.getDatetime(cursor.getString(cursor.getColumnIndex("time_receive"))));
                }
                smsList.add(sms);
            } while (cursor.moveToNext());
        }
        return smsList;
    }

    @Override
    public boolean addSms(Sms sms) {
        ContentValues values = new ContentValues();
        values.put("phone_number", sms.getPhone_number());
        values.put("content_sms", sms.getContent_sms());
        if (sms.getTime_receive() != null) {
            values.put("time_receive", TimeConvert.getStringDatetime(sms.getTime_receive()));
        }
        long isAdd = mySqlDatabase.insert("sms", null, values);
        if (isAdd == -1) return false;
        return true;
    }

    @Override
    public boolean deleteSms(int id_sms) {
        String deleteQuery = "Delete from sms where id_sms= " + id_sms;
        try {
            mySqlDatabase.execSQL(deleteQuery);
            return true;
        }catch (Exception e){
            return false;
        }

    }
}
