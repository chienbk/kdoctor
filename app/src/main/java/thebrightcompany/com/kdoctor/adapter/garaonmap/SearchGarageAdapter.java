package thebrightcompany.com.kdoctor.adapter.garaonmap;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import thebrightcompany.com.kdoctor.R;
import thebrightcompany.com.kdoctor.model.garage.GarageOnMap;
import thebrightcompany.com.kdoctor.utils.Utils;


/**
 * Created by ChienNv9 on 1/24/2018.
 */

public class SearchGarageAdapter extends RecyclerView.Adapter<SearchGarageAdapter.MyViewHolder>{

    public static final String TAG = SearchGarageAdapter.class.getSimpleName();

    private List<GarageOnMap> mList;
    private ItemSearchGarageOnClickListener mListener;
    private LatLng latLng;

    public SearchGarageAdapter(List<GarageOnMap> list, LatLng latLng, ItemSearchGarageOnClickListener listener) {
        this.mList = list;
        this.latLng = latLng;
        this.mListener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_search_garage, parent, false);
        return new MyViewHolder(itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        final GarageOnMap garageOnMap = mList.get(position);

        try {
            holder.txt_nameOfGarage.setText(garageOnMap.getName());
            holder.txt_distance.setText(Utils.distFrom(latLng, new LatLng(garageOnMap.getLat(), garageOnMap.getLng())) + " km");
            holder.txt_addressOfGarage.setText(garageOnMap.getAddress());
        }catch (NullPointerException e){
            Log.d(TAG, e.toString());
        }

        holder.layout_search_garage.setOnClickListener(new View.OnClickListener() {
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
        public LinearLayout layout_search_garage;

        public MyViewHolder(View view) {
            super(view);

            layout_search_garage = (LinearLayout) view.findViewById(R.id.layout_search_garage);

            txt_nameOfGarage = (TextView) view.findViewById(R.id.txt_nameOfGarage);
            txt_distance = (TextView) view.findViewById(R.id.txt_distance);
            txt_addressOfGarage = (TextView) view.findViewById(R.id.txt_addressOfGarage);
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
