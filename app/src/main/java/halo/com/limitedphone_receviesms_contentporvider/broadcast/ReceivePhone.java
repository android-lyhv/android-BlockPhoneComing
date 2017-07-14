package halo.com.limitedphone_receviesms_contentporvider.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import halo.com.limitedphone_receviesms_contentporvider.bean.Phone;
import halo.com.limitedphone_receviesms_contentporvider.dao.PhoneDatabase;

/**
 * Created by HoVanLy on 7/7/2016.
 */
public class ReceivePhone extends BroadcastReceiver {
    Context context;
    TelephonyManager telephonyManager;

    public ReceivePhone() {

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        // list ner phone
        MyPhoneStateListener phoneStateListener = new MyPhoneStateListener();
        // register listener for state Phone call.
        telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
    }

    private class MyPhoneStateListener extends PhoneStateListener {
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            super.onCallStateChanged(state, incomingNumber);
            Log.d("Phone Coming: ", incomingNumber);
            switch (state) {
                case TelephonyManager.CALL_STATE_IDLE:
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    break;
                case TelephonyManager.CALL_STATE_RINGING:
                    Toast.makeText(context, incomingNumber, Toast.LENGTH_LONG).show();
                    // disconnect here
                   disConnectCall(incomingNumber);
                    break;
            }
        }
    }

    private void disConnectCall(String phoneNumber) {
        if (isInListLimited(phoneNumber)) {
            try {
                Class c = Class.forName(telephonyManager.getClass().getName());
                Method m = c.getDeclaredMethod("getITelephony");
                m.setAccessible(true);
                Object object = m.invoke(telephonyManager);
                c = Class.forName(object.getClass().getName());
                m = c.getDeclaredMethod("endCall");
                m.setAccessible(true);
                m.invoke(object);
              /*  Method m2 = Telephony.getClass().getDeclaredMethod("silenceRinger");
                Method m3 = iTelephony.getClass().getDeclaredMethod("endCall");*/
                //  ITelephony telephony = (ITelephony)m.invoke(telephonyManager);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isInListLimited(String numberLimited) {
        List<Phone> listPhone = new PhoneDatabase(context).getListPhone();
        int size = listPhone.size();
        for (int i = 0; i < size; i++) {
            if (listPhone.get(i).getPhone_number().equals(numberLimited)) {
                return true;
            }
        }
        return false;
    }

}
