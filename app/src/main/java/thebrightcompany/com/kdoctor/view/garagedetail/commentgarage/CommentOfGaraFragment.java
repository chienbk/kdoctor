package thebrightcompany.com.kdoctor.view.garagedetail.commentgarage;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import thebrightcompany.com.kdoctor.R;
import thebrightcompany.com.kdoctor.model.commentgarage.DataOfComment;
import thebrightcompany.com.kdoctor.view.garagedetail.ActivityGarageDetail;

/**
 * A simple {@link Fragment} subclass.
 */
public class CommentOfGaraFragment extends Fragment implements CommentOfGaraView{

    public static final String TAG = CommentOfGaraFragment.class.getSimpleName();
    private ActivityGarageDetail homeActivity;
    private static final String ARG_ID_GARAGE = "ID_OF_GARAGE";

    @BindView(R.id.txt_rate) TextView txt_rate;
    @BindView(R.id.txt_all) TextView txt_all;
    @BindView(R.id.txt_five) TextView txt_five;
    @BindView(R.id.txt_four) TextView txt_four;
    @BindView(R.id.txt_three) TextView txt_three;
    @BindView(R.id.txt_two) TextView txt_two;
    @BindView(R.id.txt_one) TextView txt_one;

    @BindView(R.id.rc_listComment)
    RecyclerView rc_listComment;

    private int idOfGarage;

    public CommentOfGaraFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param id
     * @return
     */
    public static CommentOfGaraFragment newInstance(String id) {
        CommentOfGaraFragment fragment = new CommentOfGaraFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ID_GARAGE, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idOfGarage = Integer.parseInt(getArguments().getString(ARG_ID_GARAGE));
            Log.d(TAG, "idOfGarage: " + idOfGarage);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_comment_of_gara, container, false);
        ButterKnife.bind(this, view);
        initView(view);
        return view;
    }

    /**
     * The method use to init View
     * @param view
     */
    private void initView(View view) {
        //todo something
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.homeActivity = (ActivityGarageDetail) context;
    }

    @Override
    public void getCommentSuccess(DataOfComment dataOfComment) {
        //todo something
    }

    @Override
    public void getCommentError(String msg, int status_code) {
        homeActivity.showMessage(msg);
    }

    @Override
    public void onCommonError(String msg) {
        homeActivity.showMessage(msg);
    }

    @Override
    public void showProgress() {
        homeActivity.showProgress();
    }

    @Override
    public void hideProgress() {
        homeActivity.hideProgress();
    }

    @Override
    public void showMessage(String message) {
        homeActivity.showMessage(message);
    }

    @OnClick(R.id.layout_all)
    public void processGetAllComment(){
        showMessage("Get all comment");
    }

    @OnClick(R.id.layout_five)
    public void processGetCommentFiveStar(){
        showMessage("Get comment five star");
    }

    @OnClick(R.id.layout_four)
    public void processGetCommentFourStar(){
        showMessage("Get comment four star");
    }

    @OnClick(R.id.layout_three)
    public void processGetCommentThreeStar(){
        showMessage("Get comment three star");
    }

    @OnClick(R.id.layout_two)
    public void processGetCommentTwoStar(){
        showMessage("Get comment two star");
    }

    @OnClick(R.id.layout_one)
    public void processGetCommentOneStar(){
        showMessage("Get comment one star");
    }

}
