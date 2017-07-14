package halo.com.limitedphone_receviesms_contentporvider.recycleverview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import halo.com.limitedphone_receviesms_contentporvider.R;
import halo.com.limitedphone_receviesms_contentporvider.bean.Sms;
import halo.com.limitedphone_receviesms_contentporvider.dao.TimeConvert;

/**
 * Created by HoVanLy on 7/7/2016.
 */
public class RecyclerViewSmsAdapter extends RecyclerView.Adapter<RecyclerViewSmsAdapter.SmsViewHolder> {
    private List<Sms> smsList;
    private Context context;

    public RecyclerViewSmsAdapter(Context context, List<Sms> list) {
        this.context = context;
        this.smsList = list;
    }

    @Override
    public SmsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.item_recyclerview_sms, parent, false);
        return new SmsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SmsViewHolder holder, int position) {
        holder.tvPhoneNumber.setText(smsList.get(position).getPhone_number());
        String content_sms = smsList.get(position).getContent_sms();
        if (content_sms.length() > 10) {
            holder.tvTitleSms.setText(content_sms.substring(0, 10) + "...");
        } else {
            holder.tvTitleSms.setText(content_sms);
        }
        if (smsList.get(position).getTime_receive()!=null){
            holder.tvTime.setText(TimeConvert.getStringDatetime(smsList.get(position).getTime_receive()));
        }
    }

    @Override
    public int getItemCount() {
        if (smsList != null)
            return smsList.size();
        return 0;
    }

    public class SmsViewHolder extends RecyclerView.ViewHolder {
        TextView tvPhoneNumber;
        TextView tvTitleSms;
        TextView tvTime;

        public SmsViewHolder(View itemView) {
            super(itemView);
            tvPhoneNumber = (TextView) itemView.findViewById(R.id.tvPhoneNumber);
            tvTitleSms = (TextView) itemView.findViewById(R.id.tvTitleSms);
            tvTime = (TextView) itemView.findViewById(R.id.tvTime);
        }
    }

    public List<Sms> getSmsList() {
        return smsList;
    }

    public void setSmsList(List<Sms> smsList) {
        this.smsList = smsList;
    }
}
