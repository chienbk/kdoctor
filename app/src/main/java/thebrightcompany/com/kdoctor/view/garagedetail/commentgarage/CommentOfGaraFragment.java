package thebrightcompany.com.kdoctor.view.garagedetail.commentgarage;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.recyclerview.adapters.SlideInLeftAnimationAdapter;
import jp.wasabeef.recyclerview.animators.SlideInDownAnimator;
import thebrightcompany.com.kdoctor.R;
import thebrightcompany.com.kdoctor.adapter.comment.CommentOfGarageAdapter;
import thebrightcompany.com.kdoctor.model.commentgarage.Comment;
import thebrightcompany.com.kdoctor.model.commentgarage.DataOfComment;
import thebrightcompany.com.kdoctor.presenter.commentofgarage.CommentOfGaragePresentor;
import thebrightcompany.com.kdoctor.presenter.commentofgarage.CommentOfGaragePresentorImpl;
import thebrightcompany.com.kdoctor.utils.Contains;
import thebrightcompany.com.kdoctor.utils.SharedPreferencesUtils;
import thebrightcompany.com.kdoctor.utils.Utils;
import thebrightcompany.com.kdoctor.utils.VerticalSpaceItemDecoration;
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
    private DataOfComment mDataOfComment;
    private List<Comment> mList = new ArrayList<>();
    private CommentOfGarageAdapter adapter;

    private int idOfGarage;

    private CommentOfGaragePresentor presentor;
    private SharedPreferencesUtils sharedPreferencesUtils;
    private String rate = "0";

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
        sharedPreferencesUtils = new SharedPreferencesUtils(homeActivity);
        rate = sharedPreferencesUtils.readStringPreference(Contains.PREF_RATE, "0");
        txt_rate.setText(rate);
        presentor = new CommentOfGaragePresentorImpl(this);
        presentor.processGetCommentDetail(Utils.APP_TOKEN, String.valueOf(idOfGarage), "10", 0, "all");

        adapter = new CommentOfGarageAdapter(mList);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        rc_listComment.setLayoutManager(mLayoutManager);
        rc_listComment.setItemAnimator(new SlideInDownAnimator());
        rc_listComment.setAdapter(new SlideInLeftAnimationAdapter(adapter));
        rc_listComment.addItemDecoration(new VerticalSpaceItemDecoration(35));

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.homeActivity = (ActivityGarageDetail) context;
    }

    @Override
    public void getCommentSuccess(String token, DataOfComment dataOfComment) {
        //todo something
        homeActivity.updateToken(token);
        Utils.APP_TOKEN = token;
        this.mDataOfComment = dataOfComment;
        this.mList.clear();
        this.mList = mDataOfComment.getComments();
        //todo update view
        try {
            txt_all.setText("Tất cả (" + dataOfComment.getSummaryStar().getTotal_of_comments() +")");
            txt_five.setText("5 sao (" + dataOfComment.getSummaryStar().getTotal_of_5_star_comments() +")");
            txt_four.setText("4 sao (" + dataOfComment.getSummaryStar().getTotal_of_4_star_comments() +")");
            txt_three.setText("3 sao (" + dataOfComment.getSummaryStar().getTotal_of_3_star_comments() +")");
            txt_two.setText("2 sao (" + dataOfComment.getSummaryStar().getTotal_of_2_star_comments() +")");
            txt_one.setText("1 sao (" + dataOfComment.getSummaryStar().getTotal_of_1_star_comments() +")");
            mList.clear();
            mList = dataOfComment.getComments();
            adapter.notifyDataSetChanged(mList);
        }catch (NullPointerException e){
            Log.d(TAG, e.toString());
        }
    }

    @Override
    public void getCommentError(String msg, int status_code) {
        showMessage(msg);
        if (status_code == Contains.TOKEN_EXPIRED){
            homeActivity.logout();
        }

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
        //showMessage("Get all comment");
        presentor.processGetCommentDetail(Utils.APP_TOKEN, String.valueOf(idOfGarage), "10", 0, "all");
    }

    @OnClick(R.id.layout_five)
    public void processGetCommentFiveStar(){
        //showMessage("Get comment five star");
        presentor.processGetCommentDetail(Utils.APP_TOKEN, String.valueOf(idOfGarage), "10", 0, "5");
    }

    @OnClick(R.id.layout_four)
    public void processGetCommentFourStar(){
        //showMessage("Get comment four star");
        presentor.processGetCommentDetail(Utils.APP_TOKEN, String.valueOf(idOfGarage), "10", 0, "4");
    }

    @OnClick(R.id.layout_three)
    public void processGetCommentThreeStar(){
        //showMessage("Get comment three star");
        presentor.processGetCommentDetail(Utils.APP_TOKEN, String.valueOf(idOfGarage), "10", 0, "3");
    }

    @OnClick(R.id.layout_two)
    public void processGetCommentTwoStar(){
        //showMessage("Get comment two star");
        presentor.processGetCommentDetail(Utils.APP_TOKEN, String.valueOf(idOfGarage), "10", 0, "2");
    }

    @OnClick(R.id.layout_one)
    public void processGetCommentOneStar(){
        //showMessage("Get comment one star");
        presentor.processGetCommentDetail(Utils.APP_TOKEN, String.valueOf(idOfGarage), "10", 0, "1");
    }

}
