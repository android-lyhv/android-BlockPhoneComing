package halo.com.limitedphone_receviesms_contentporvider.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import halo.com.limitedphone_receviesms_contentporvider.bean.Phone;

/**
 * Created by HoVanLy on 7/7/2016.
 */
public class PhoneDatabase extends AbstractDataBaseHelper implements IPhoneDatabase {
    /**
     * Constructor
     * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
     *
     * @param context
     */
    private SQLiteDatabase mySqlDatabase;

    public PhoneDatabase(Context context) {
        super(context);
        mySqlDatabase = getSQLiteDatabase();
    }

    @Override
    public List<Phone> getListPhone() {
        List<Phone> phoneList = new ArrayList<>();
        String sqlQuery = "Select * from phone";
        Cursor cursor = mySqlDatabase.rawQuery(sqlQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Phone phone = new Phone();
                phone.setId_phone(cursor.getInt(cursor.getColumnIndex("id_phone")));
                phone.setPhone_number(cursor.getString(cursor.getColumnIndex("phone_number")));
                phoneList.add(phone);
            } while (cursor.moveToNext());
        }
        return phoneList;
    }

    @Override
    public boolean addPhone(Phone phone) {
        ContentValues values = new ContentValues();
        values.put("phone_number", phone.getPhone_number());
        long insert = mySqlDatabase.insert("phone", null, values);
        if (insert == -1) return false;
        return true;
    }

    @Override
    public boolean deletePhone(int id_phone) {
        String deleteQuery = "Delete from phone where id_phone= " + id_phone;
        try {
            mySqlDatabase.execSQL(deleteQuery);
            return true;

        } catch (Exception e) {
            return false;
        }
    }
}
