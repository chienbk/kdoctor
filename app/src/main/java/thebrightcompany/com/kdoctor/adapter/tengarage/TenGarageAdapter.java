package thebrightcompany.com.kdoctor.adapter.tengarage;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import thebrightcompany.com.kdoctor.R;
import thebrightcompany.com.kdoctor.model.garage.GarageOnMap;
import thebrightcompany.com.kdoctor.utils.Utils;


/**
 * Created by ChienNv9 on 1/24/2018.
 */

public class TenGarageAdapter extends RecyclerView.Adapter<TenGarageAdapter.MyViewHolder>{

    private List<GarageOnMap> mList;
    private ItemTenGarageOnClickListener mListener;
    private ItemSubmitGarageOnClickListener listener;
    private LatLng latLng;

    public TenGarageAdapter(List<GarageOnMap> list, LatLng latLng, ItemTenGarageOnClickListener listener,
                            ItemSubmitGarageOnClickListener onClickListener) {
        this.mList = list;
        this.latLng = latLng;
        this.mListener = listener;
        this.listener = onClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_ten_garage, parent, false);
        return new MyViewHolder(itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        final GarageOnMap garageOnMap = mList.get(position);

        try {

            holder.txt_nameOfGarage.setText(garageOnMap.getName());
            holder.txt_distance.setText(Utils.calculationByDistance(latLng, new LatLng(garageOnMap.getLat(), garageOnMap.getLng()))+ " km");
            holder.btn_direct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClickListener(true, garageOnMap);
                }
            });
            holder.txt_addressOfGarage.setText(garageOnMap.getAddress());
            holder.rate_garage.setRating(garageOnMap.getRate());

            holder.btn_contact.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClickListener(false, garageOnMap);
                }
            });
        }catch (NullPointerException e){
            Log.d("Diagnostic Adapter: ", e.toString());
        }

        holder.layout_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onItemClickListener(position, garageOnMap);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView txt_nameOfGarage, txt_distance, txt_addressOfGarage;
        public RatingBar rate_garage;
        public LinearLayout layout_detail;
        public Button btn_direct, btn_contact;

        public MyViewHolder(View view) {
            super(view);
            txt_nameOfGarage = (TextView) view.findViewById(R.id.txt_nameOfGarage);
            txt_distance = (TextView) view.findViewById(R.id.txt_distance);
            txt_addressOfGarage = (TextView) view.findViewById(R.id.txt_addressOfGarage);
            rate_garage = (RatingBar) view.findViewById(R.id.rate_garage);
            layout_detail = (LinearLayout) view.findViewById(R.id.layout_detail);
            btn_direct = (Button) view.findViewById(R.id.btn_direct);
            btn_contact = (Button) view.findViewById(R.id.btn_contact);
        }
    }

    public void notifyDataSetChanged(List<GarageOnMap> garageOnMaps) {
        if(mList != null){
            mList.clear();
            this.mList.addAll(garageOnMaps);
            this.notifyDataSetChanged();
        }
    }

    public void clearDataSetChanged() {
        this.mList.clear();
        this.notifyDataSetChanged();
    }

    public void notifyItemChange(int position, GarageOnMap garageOnMap){
        mList.remove(position);
        notifyItemRemoved(position);
        mList.add(position, garageOnMap);
        notifyItemInserted(position);
    }
}
