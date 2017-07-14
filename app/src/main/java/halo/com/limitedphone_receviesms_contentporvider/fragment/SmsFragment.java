package halo.com.limitedphone_receviesms_contentporvider.fragment;

import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Telephony;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import halo.com.limitedphone_receviesms_contentporvider.R;
import halo.com.limitedphone_receviesms_contentporvider.activity.DeleteDialog;
import halo.com.limitedphone_receviesms_contentporvider.activity.DetailSmsActivity;
import halo.com.limitedphone_receviesms_contentporvider.bean.Sms;
import halo.com.limitedphone_receviesms_contentporvider.dao.SmsDatabase;
import halo.com.limitedphone_receviesms_contentporvider.dao.TimeConvert;
import halo.com.limitedphone_receviesms_contentporvider.recycleverview.ClickItemRecyclerView;
import halo.com.limitedphone_receviesms_contentporvider.recycleverview.DividerItemDecoration;
import halo.com.limitedphone_receviesms_contentporvider.recycleverview.IClickItem;
import halo.com.limitedphone_receviesms_contentporvider.recycleverview.RecyclerViewSmsAdapter;

/**
 * Created by HoVanLy on 7/7/2016.
 */
public class SmsFragment extends Fragment {
    public static RecyclerViewSmsAdapter smsAdapter;
    RecyclerView recyclerView;
    List<Sms> smsList;
    SmsDatabase smsDatabase;
    static Context context;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        smsDatabase = new SmsDatabase(getContext());
        smsList = smsDatabase.getListsSms();
        smsAdapter = new RecyclerViewSmsAdapter(getContext(), smsList);
        context = this.getContext();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sms_fragment, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recSms);
        loadRecyclerView();
        return view;
    }

    private void loadRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        recyclerView.hasFixedSize();
        recyclerView.setAdapter(smsAdapter);
        recyclerView.addOnItemTouchListener(new ClickItemRecyclerView(getContext(), recyclerView, new IClickItem() {
            @Override
            public void onClick(View view, int position) {
                getAllSmsFromProvider();
                Intent intent = new Intent(getContext(), DetailSmsActivity.class);
                intent.putExtra("sms", smsAdapter.getSmsList().get(position));
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {
                Date dateDelete = smsDatabase.getListsSms().get(position).getTime_receive();
                DeleteDialog deleteDialog = new DeleteDialog();
                deleteDialog.showDialog(deleteDialog.onCreateDialog(getContext(),
                        smsDatabase.getListsSms().get(position).getId_sms(), dateDelete));
            }
        }));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        smsList = smsDatabase.getListsSms();
        smsAdapter.setSmsList(smsList);
        smsAdapter.notifyDataSetChanged();
        smsDatabase.close();
    }

    @Override
    public void onResume() {
        super.onResume();
        smsList = smsDatabase.getListsSms();
        smsAdapter.setSmsList(smsList);
        smsAdapter.notifyDataSetChanged();
    }

    public static void refreshListSms() {
        SmsDatabase smsDatabase = new SmsDatabase(context);
        smsAdapter.setSmsList(smsDatabase.getListsSms());
        smsAdapter.notifyDataSetChanged();
        smsDatabase.close();
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public List<String> getAllSmsFromProvider() {
        List<String> lstSms = new ArrayList<String>();
        ContentResolver cr = context.getContentResolver();

        Cursor c = cr.query(Telephony.Sms.Inbox.CONTENT_URI, // Official CONTENT_URI from docs
                new String[]{Telephony.Sms.Inbox.DATE}, // Select body text
                null,
                null,
                Telephony.Sms.DEFAULT_SORT_ORDER); // Default sort order

        int totalSMS = c.getCount();

        if (c.moveToFirst()) {
            for (int i = 0; i < totalSMS; i++) {
                lstSms.add(c.getString(0));
                c.moveToNext();
            }
        } else {
            throw new RuntimeException("You have no SMS in Inbox");
        }
        long datetime = Long.parseLong(lstSms.get(0));
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(datetime);
        Date date = calendar.getTime();
        String time_receive = TimeConvert.getStringDatetime(date);
        Toast.makeText(getContext(), time_receive, Toast.LENGTH_LONG).show();
        c.close();

        return lstSms;
    }

}
