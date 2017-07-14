package halo.com.limitedphone_receviesms_contentporvider.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import halo.com.limitedphone_receviesms_contentporvider.R;
import halo.com.limitedphone_receviesms_contentporvider.bean.Phone;
import halo.com.limitedphone_receviesms_contentporvider.dao.PhoneDatabase;

/**
 * Created by HoVanLy on 7/8/2016.
 */
public class AddLimitedPhone extends AppCompatActivity implements View.OnClickListener {
    Button btnYes;
    Button btnNo;
    ImageView imNumberFromContact;
    EditText edtPhone;
    static final int PICK_CONTACT = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_phone_limited);
        btnYes = (Button) findViewById(R.id.btnYes);
        btnNo = (Button) findViewById(R.id.btnNo);
        imNumberFromContact = (ImageView) findViewById(R.id.imNumberFromContact);
        imNumberFromContact.setOnClickListener(this);
        btnNo.setOnClickListener(this);
        btnYes.setOnClickListener(this);
        edtPhone = (EditText) findViewById(R.id.edtPhone);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btnYes:
                addPhoneNumber();
                break;
            case R.id.btnNo:
                finish();
                break;
            case R.id.imNumberFromContact:
                pickPhoneFormContact();
                break;
        }
    }

    private void addPhoneNumber() {
        String phoneNumber = edtPhone.getText().toString();
        if (!"".equals(phoneNumber)) {
            PhoneDatabase phoneDatabase = new PhoneDatabase(getApplicationContext());
            Phone phone = new Phone();
            phone.setPhone_number(phoneNumber);
            phoneDatabase.addPhone(phone);
            phoneDatabase.close();
            finish();
        }
    }

    private void pickPhoneFormContact() {
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(intent, PICK_CONTACT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case (PICK_CONTACT):
                if (resultCode == Activity.RESULT_OK) {

                    Uri contactData = data.getData();
                    Cursor c = managedQuery(contactData, null, null, null, null);
                    if (c.moveToFirst()) {

                        String id = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));

                        String hasPhone = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

                        if (hasPhone.equalsIgnoreCase("1")) {
                            Cursor phones = getContentResolver().query(
                                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id,
                                    null, null);
                            phones.moveToFirst();
                            edtPhone.setText(phones.getString(phones.getColumnIndex("data1")).trim());
                        }
                    }
                }
                break;
        }
    }
}
