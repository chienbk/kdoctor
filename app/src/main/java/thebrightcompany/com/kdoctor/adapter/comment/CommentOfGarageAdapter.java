package thebrightcompany.com.kdoctor.adapter.comment;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

import thebrightcompany.com.kdoctor.R;
import thebrightcompany.com.kdoctor.model.commentgarage.Comment;
import thebrightcompany.com.kdoctor.utils.Utils;

/**
 * Created by ChienNv9 on 1/24/2018.
 */

public class CommentOfGarageAdapter extends RecyclerView.Adapter<CommentOfGarageAdapter.MyViewHolder>{

    private List<Comment> mList;

    public CommentOfGarageAdapter(List<Comment> list) {
        this.mList = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_comment, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        final Comment comment = mList.get(position);

        try {
            holder.txt_nameOfUser.setText(comment.getCustomer_name());
            holder.rate_comment.setRating(comment.getRating());
            holder.txt_description.setText(comment.getComment());
            holder.txt_date.setText(Utils.convertTime(comment.getTime()));

        }catch (NullPointerException e){
            Log.d("Diagnostic Adapter: ", e.toString());
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView txt_nameOfUser, txt_description, txt_date;
        public RatingBar rate_comment;
        public ImageView imgAvatar;

        public MyViewHolder(View view) {
            super(view);
            txt_nameOfUser = (TextView) view.findViewById(R.id.txt_nameOfUser);
            txt_description = (TextView) view.findViewById(R.id.txt_description);
            txt_date = (TextView) view.findViewById(R.id.txt_date);
            rate_comment = (RatingBar) view.findViewById(R.id.rate_comment);
            imgAvatar = (ImageView) view.findViewById(R.id.imgAvatar);
        }
    }

    public void notifyDataSetChanged(List<Comment> comments) {
        if(mList != null){
            mList.clear();
            this.mList.addAll(comments);
            this.notifyDataSetChanged();
        }
    }

    public void clearDataSetChanged() {
        this.mList.clear();
        this.notifyDataSetChanged();
    }

    public void notifyItemChange(int position, Comment comment){
        mList.remove(position);
        notifyItemRemoved(position);
        mList.add(position, comment);
        notifyItemInserted(position);
    }
}
