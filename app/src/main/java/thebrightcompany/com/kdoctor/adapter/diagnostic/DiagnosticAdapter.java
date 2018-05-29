package thebrightcompany.com.kdoctor.adapter.diagnostic;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import thebrightcompany.com.kdoctor.R;
import thebrightcompany.com.kdoctor.model.diagnostic.Diagnostic;


/**
 * Created by ChienNv9 on 1/24/2018.
 */

public class DiagnosticAdapter extends RecyclerView.Adapter<DiagnosticAdapter.MyViewHolder>{

    private List<Diagnostic> mList;
    private ItemDiagnosticOnClickListener mListener;

    public DiagnosticAdapter(List<Diagnostic> list, ItemDiagnosticOnClickListener listener) {
        this.mList = list;
        this.mListener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_diagnostic, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        final Diagnostic diagnostic = mList.get(position);

        try {
            holder.txt_title.setText(diagnostic.getName() + "");
            holder.txt_data.setText(diagnostic.getValue() + " " + diagnostic.getUnit());
            holder.txt_description.setText(diagnostic.getDescription() + "");
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

        public TextView txt_title, txt_data, txt_description;
        public LinearLayout layout_diagnostic;

        public MyViewHolder(View view) {
            super(view);

            layout_diagnostic = (LinearLayout) view.findViewById(R.id.layout_diagnostic);
            txt_title = (TextView) view.findViewById(R.id.txt_title);
            txt_data = (TextView) view.findViewById(R.id.txt_data);
            txt_description = (TextView) view.findViewById(R.id.tvDescription);
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
