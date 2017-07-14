package halo.com.limitedphone_receviesms_contentporvider.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import halo.com.limitedphone_receviesms_contentporvider.R;
import halo.com.limitedphone_receviesms_contentporvider.bean.Phone;
import halo.com.limitedphone_receviesms_contentporvider.dao.PhoneDatabase;
import halo.com.limitedphone_receviesms_contentporvider.recycleverview.ClickItemRecyclerView;
import halo.com.limitedphone_receviesms_contentporvider.recycleverview.DividerItemDecoration;
import halo.com.limitedphone_receviesms_contentporvider.recycleverview.IClickItem;
import halo.com.limitedphone_receviesms_contentporvider.recycleverview.RecyclerViewPhoneAdapter;

/**
 * Created by HoVanLy on 7/7/2016.
 */
public class PhoneFragment extends Fragment {
    RecyclerView recyclerView;
    PhoneDatabase phoneDatabase;
    List<Phone> phoneList;
    RecyclerViewPhoneAdapter phoneAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        phoneDatabase = new PhoneDatabase(getContext());
        phoneList = phoneDatabase.getListPhone();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.limited_phone_fragment, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recLimitedPhone);
        loadRecyclerView();
        return view;
    }

    private void loadRecyclerView() {
        phoneAdapter = new RecyclerViewPhoneAdapter(getContext(), phoneList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.hasFixedSize();
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(phoneAdapter);
        recyclerView.addOnItemTouchListener(new ClickItemRecyclerView(getContext(), recyclerView, new IClickItem() {
            @Override
            public void onClick(View view, int position) {

            }

            @Override
            public void onLongClick(View view, int position) {
                PopupMenu popupMenu = new PopupMenu(getContext(), view);
                MenuInflater menuInflater = popupMenu.getMenuInflater();
                menuInflater.inflate(R.menu.menu_config, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new MyMenuClick(position));
                popupMenu.show();
            }
        }));
    }

    @Override
    public void onResume() {
        super.onResume();
        phoneList = phoneDatabase.getListPhone();
        phoneAdapter.setPhoneList(phoneList);
        phoneAdapter.notifyDataSetChanged();
    }


    private class MyMenuClick implements PopupMenu.OnMenuItemClickListener {
        int position;
        public MyMenuClick(int position) {
            this.position = position;
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_delete:
                    deleteOnDb(phoneList.get(position).getId_phone());
                    phoneList.remove(position);
                    phoneAdapter.notifyItemRemoved(position);
                    break;
            }
            return false;
        }
    }

    public void deleteOnDb(int id_phone) {
        phoneDatabase.deletePhone(id_phone);
    }

}
