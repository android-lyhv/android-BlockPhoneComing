package halo.com.limitedphone_receviesms_contentporvider.recycleverview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import halo.com.limitedphone_receviesms_contentporvider.R;
import halo.com.limitedphone_receviesms_contentporvider.bean.Phone;

/**
 * Created by HoVanLy on 7/7/2016.
 */
public class RecyclerViewPhoneAdapter extends RecyclerView.Adapter<RecyclerViewPhoneAdapter.PhoneViewHolder> {
    private List<Phone> phoneList;
    private Context context;

    public RecyclerViewPhoneAdapter(Context context, List<Phone> list) {
        this.context = context;
        this.phoneList = list;
    }

    @Override
    public PhoneViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.item_recyclerview_limited_phone, parent, false);
        return new PhoneViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PhoneViewHolder holder, int position) {
        holder.tvPhoneNumber.setText(phoneList.get(position).getPhone_number());

    }

    @Override
    public int getItemCount() {
        if (phoneList != null)
            return phoneList.size();
        return 0;
    }

    public class PhoneViewHolder extends RecyclerView.ViewHolder {
        TextView tvPhoneNumber;


        public PhoneViewHolder(View itemView) {
            super(itemView);
            tvPhoneNumber = (TextView) itemView.findViewById(R.id.tvPhoneNumber);
        }
    }

    public List<Phone> getPhoneList() {
        return phoneList;
    }

    public void setPhoneList(List<Phone> phoneList) {
        this.phoneList = phoneList;
    }
}
