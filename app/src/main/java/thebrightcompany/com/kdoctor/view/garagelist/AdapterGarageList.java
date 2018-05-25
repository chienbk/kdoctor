package thebrightcompany.com.kdoctor.view.garagelist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import thebrightcompany.com.kdoctor.R;
import thebrightcompany.com.kdoctor.model.garage.Garage;

/**
 * Created by CongVC on 5/25/2018.
 */

public class AdapterGarageList extends RecyclerView.Adapter<AdapterGarageList.ItemViewGarage> {
    private Context mContext;
    private ArrayList<Garage> mGarageArrayList;
    private OnClickItemListener mOnClickItemListener;
    private OnClickDirectListener mOnClickDirectListener;
    private OnClickContactListener mOnClickContactListener;

    public AdapterGarageList(Context context, ArrayList<Garage> garageArrayList) {
        mGarageArrayList = garageArrayList;
        mContext = context;
    }

//    public void more(ArrayList<Garage> garageArrayList){
//        mGarageArrayList.addAll(garageArrayList);
//        notifyDataSetChanged();
//    }

    @Override
    public ItemViewGarage onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_garage, parent, false);

        return new ItemViewGarage(view);
    }

    @Override
    public void onBindViewHolder(ItemViewGarage holder, int position) {
        Garage garage = mGarageArrayList.get(position);
        holder.tvName.setText(garage.getName());
        holder.tvAddress.setText(garage.getAddress());
        holder.tvVote.setText(garage.getVote());

        if (garage.getImageUrl() != null && !garage.getImageUrl().equals("")) {
            Glide.with(mContext).load(garage.getImageUrl()).into(holder.imgAvatar);
        }
        holder.btDirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnClickDirectListener != null) {
                    mOnClickDirectListener.onClick();
                }
            }
        });
        holder.btContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnClickContactListener != null) {
                    mOnClickContactListener.onClick();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mGarageArrayList.size();
    }

    public class ItemViewGarage extends RecyclerView.ViewHolder {
        ImageView imgAvatar;
        TextView tvName, tvVote, tvAddress;
        Button btDirect, btContact;

        public ItemViewGarage(View view) {
            super(view);
            imgAvatar = (ImageView) view.findViewById(R.id.imgAvatar);
            tvName = (TextView) view.findViewById(R.id.tvName);
            tvVote = (TextView) view.findViewById(R.id.tvVote);
            tvAddress = (TextView) view.findViewById(R.id.tvAddress);
            btDirect = (Button) view.findViewById(R.id.btDirect);
            btContact = (Button) view.findViewById(R.id.btContact);
        }
    }

    public void setOnClickItemListener(OnClickItemListener onClickItemListener) {
        mOnClickItemListener = onClickItemListener;
    }

    public void setOnClickDirectListener(OnClickDirectListener onClickDirectListener) {
        mOnClickDirectListener = onClickDirectListener;

    }

    public void setOnClickContactListener(OnClickContactListener onClickContactListener) {
        mOnClickContactListener = onClickContactListener;

    }

    public interface OnClickItemListener {
        public void onClick(Garage garage);
    }

    public interface OnClickContactListener {
        public void onClick();
    }

    public interface OnClickDirectListener {
        public void onClick();
    }
}
