package thebrightcompany.com.kdoctor.adapter.diagnostic;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import thebrightcompany.com.kdoctor.R;
import thebrightcompany.com.kdoctor.model.diagnostic.Diagnostic;


/**
 * Created by ChienNv9 on 1/24/2018.
 */

public class DiagnosticsAdapter extends RecyclerView.Adapter<DiagnosticsAdapter.MyViewHolder>{

    private List<Diagnostic> mList;
    private ItemDiagnosticOnClickListener mListener;
    private Context context;

    public DiagnosticsAdapter(Context context, List<Diagnostic> list, ItemDiagnosticOnClickListener listener) {
        this.mList = list;
        this.mListener = listener;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_diagnostic_new, parent, false);
        return new MyViewHolder(itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        final Diagnostic diagnostic = mList.get(position);

        try {
            int value = Integer.parseInt(diagnostic.getValue());
            int maxValue = Integer.parseInt(diagnostic.getMax());
            int minValue = Integer.parseInt(diagnostic.getMin());
            Log.d("DiagnosticsAdapter", "Max: " + maxValue );
            Log.d("DiagnosticsAdapter", "Min: " + minValue );
            Log.d("DiagnosticsAdapter", "value: " + value );
            int diff = 0;

            if (value < minValue){
                diff = minValue - value;
                Log.d("DiagnosticsAdapter", "diff: " + diff );
                holder.layout_header.setBackgroundColor(context.getColor(R.color.color_diagnostic_less));
                holder.txt_level.setBackgroundColor(context.getColor(R.color.color_diagnostic_less));
                holder.txt_level.setText(diff + " " + diagnostic.getUnit());
                holder.layout_max.setVisibility(View.VISIBLE);
                holder.layout_min.setVisibility(View.VISIBLE);
            }
            if (value > maxValue){
                diff = value - maxValue;
                Log.d("DiagnosticsAdapter", "diff: " + diff );
                holder.layout_header.setBackgroundColor(context.getColor(R.color.color_diagnostic_greater));
                holder.txt_level.setBackgroundColor(context.getColor(R.color.color_diagnostic_greater));
                holder.txt_level.setText(diff + " " + diagnostic.getUnit());
                holder.layout_max.setVisibility(View.VISIBLE);
                holder.layout_min.setVisibility(View.VISIBLE);
            }
            if (minValue <= value && value <= maxValue){
                holder.layout_header.setBackgroundColor(context.getColor(R.color.color_diagnostic_normal));
                holder.layout_max.setVisibility(View.INVISIBLE);
                holder.layout_min.setVisibility(View.INVISIBLE);
            }

            holder.txt_data.setText(diagnostic.getValue() + " " + diagnostic.getUnit());
            holder.txt_title.setText(diagnostic.getName());
            holder.txt_min.setText(diagnostic.getMin() + " " + diagnostic.getUnit());
            holder.txt_max.setText(diagnostic.getMax() + " " + diagnostic.getUnit());
        }catch (NullPointerException e){
            Log.d("Diagnostic Adapter: ", e.toString());
        }

        holder.layout_diagnostic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onItemClickListener(position, diagnostic);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView txt_title, txt_data, txt_max, txt_min, txt_level, txt_level_data;
        public LinearLayout layout_diagnostic, layout_max, layout_min;
        public RelativeLayout layout_header;

        public MyViewHolder(View view) {
            super(view);

            layout_diagnostic = (LinearLayout) view.findViewById(R.id.layout_diagnostic);
            layout_max = (LinearLayout) view.findViewById(R.id.layout_level_2);
            layout_min = (LinearLayout) view.findViewById(R.id.layout_level_1);
            layout_header = (RelativeLayout) view.findViewById(R.id.layout_header);

            txt_title = (TextView) view.findViewById(R.id.txt_nameOfDiagnostic);
            txt_data = (TextView) view.findViewById(R.id.txt_data);
            txt_max = (TextView) view.findViewById(R.id.txt_max);
            txt_min = (TextView) view.findViewById(R.id.txt_min);
            txt_level = (TextView) view.findViewById(R.id.txt_level);
            txt_level_data = (TextView) view.findViewById(R.id.txt_level_data);

        }
    }

    public void notifyDataSetChanged(List<Diagnostic> diagnostics) {
        if(mList != null){
            mList.clear();
            this.mList.addAll(diagnostics);
            this.notifyDataSetChanged();
        }
    }

    public void clearDataSetChanged() {
        this.mList.clear();
        this.notifyDataSetChanged();
    }

    public void notifyItemChange(int position, Diagnostic diagnostic){
        mList.remove(position);
        notifyItemRemoved(position);
        mList.add(position, diagnostic);
        notifyItemInserted(position);
    }
}
