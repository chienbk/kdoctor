package thebrightcompany.com.kdoctor.adapter.troublecode;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import thebrightcompany.com.kdoctor.R;
import thebrightcompany.com.kdoctor.model.troublecode.TroubleCode;


/**
 * Created by ChienNv9 on 1/24/2018.
 */

public class TroubleCodeAdapter extends RecyclerView.Adapter<TroubleCodeAdapter.MyViewHolder>{

    public static final String TAG = TroubleCodeAdapter.class.getSimpleName();
    private List<TroubleCode> mList;
    private ItemTroubleCodeOnClickListener mListener;

    public TroubleCodeAdapter(List<TroubleCode> list, ItemTroubleCodeOnClickListener listener) {
        this.mList = list;
        this.mListener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_trouble_code, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        final TroubleCode troubleCode = mList.get(position);

        try {
            holder.txt_code.setText(troubleCode.getCode());
            holder.txt_level.setText(troubleCode.getImportance_level() + "");
            holder.txt_detailOfError.setText(troubleCode.getSummary_vi());

            holder.btn_viewDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onItemClickListener(position, troubleCode);
                }
            });
        }catch (Exception e){
            Log.d(TAG, e.toString());
        }

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView txt_code, txt_level, txt_detailOfError;
        public Button btn_viewDetail;

        public MyViewHolder(View view) {
            super(view);

        }
    }

    public void notifyDataSetChanged(List<TroubleCode> troubleCodes) {
        if(mList != null){
            mList.clear();
            this.mList.addAll(troubleCodes);
            this.notifyDataSetChanged();
        }
    }

    public void clearDataSetChanged() {
        this.mList.clear();
        this.notifyDataSetChanged();
    }

    public void notifyItemChange(int position, TroubleCode diagnostic){
        mList.remove(position);
        notifyItemRemoved(position);
        mList.add(position, diagnostic);
        notifyItemInserted(position);
    }
}
