package thebrightcompany.com.kdoctor.view.home.fragment.history;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import thebrightcompany.com.kdoctor.R;
import thebrightcompany.com.kdoctor.view.home.HomeActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends Fragment implements HistoryView{

    public static final String TAG = HistoryFragment.class.getSimpleName();
    private HomeActivity homeActivity;

    public HistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        ButterKnife.bind(this, view);
        initView(view);
        return view;
    }

    private void initView(View view) {
        homeActivity.setTitle("Lịch sử sửa chữa");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.homeActivity = (HomeActivity) context;
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

    @Override
    public void onResume() {
        super.onResume();
        homeActivity.setTitle("Lịch sử sửa chữa");
    }
}
