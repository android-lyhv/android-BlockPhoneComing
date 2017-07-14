package halo.com.limitedphone_receviesms_contentporvider.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.provider.Telephony;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import java.util.Date;

import halo.com.limitedphone_receviesms_contentporvider.dao.SmsDatabase;
import halo.com.limitedphone_receviesms_contentporvider.fragment.SmsFragment;

/**
 * Created by HoVanLy on 4/25/2016.
 */
public class DeleteDialog extends DialogFragment {
    Context myContext;

    public Dialog onCreateDialog(final Context context, final int id_sms, final Date date) {
        this.myContext = context;
        // Use the Builder class for convenient dialog construction
        final AlertDialog.Builder builder = new AlertDialog.Builder(myContext);
        builder.setMessage("Delete this sms?")
                .setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // FIRE ZE MISSILES!
                    }
                })
                .setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(myContext, "Delete", Toast.LENGTH_SHORT).show();
                        SmsDatabase smsDatabase = new SmsDatabase(myContext);
                        smsDatabase.deleteSms(id_sms);
                        smsDatabase.close();
                        SmsFragment.refreshListSms();
                        // Delete sms from content provider
                       //deleteAllMessage(myContext);
                        deleteMessageByDate(myContext, date);
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }

    public void showDialog(Dialog dialog) {
        dialog.show();
    }

    private int deleteAllMessage(Context context) {
        Uri deleteUri = Uri.parse("content://sms");
        int count = 0;
        Cursor cursor = context.getContentResolver().query(deleteUri, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                // Delete the SMS
                String pid = cursor.getString(0); // Get id;
                String uri = "content://sms/" + pid;
                count = context.getContentResolver().delete(Uri.parse(uri),
                        null, null);
            } while (cursor.moveToNext());
        }
        return count;


    }

    private void deleteMessageByDate(Context context, Date myDate) {
        Uri deleteUri = Uri.parse("content://sms");
        Cursor cursor = context.getContentResolver().query(deleteUri, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                // Delete the SMS
                String id = cursor.getString(0); // Get id;
                long date_time = cursor.getLong(cursor.getColumnIndexOrThrow(Telephony.Sms.DATE));
                if (myDate.getTime() == date_time) {
                    Toast.makeText(context,date_time+":" + myDate.getTime(), Toast.LENGTH_LONG).show();
                    String uri = "content://sms/" + id;
                    context.getContentResolver().delete(Uri.parse(uri), null, null);
                }

            } while (cursor.moveToNext());
        }

    }

    private void deleteSmsByPhoneNumber(Context context, String fromAddress) {
        Uri uriSMS = Uri.parse("content://sms/inbox");
        Cursor cursor = context.getContentResolver().query(uriSMS, null,
                null, null, null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            int ThreadId = cursor.getInt(1);
            Log.d("Thread Id", ThreadId + " id - " + cursor.getInt(0));
            Log.d("contact number", cursor.getString(2));
            Log.d("column name", cursor.getColumnName(2));

            context.getContentResolver().delete(Uri.
                            parse("content://sms/conversations/" + ThreadId), "address=?",
                    new String[]{fromAddress});
            Log.d("Message Thread Deleted", fromAddress);
        }

    }
}
