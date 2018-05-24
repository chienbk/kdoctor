package thebrightcompany.com.kdoctor.view.home.fragment.troublecode;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import thebrightcompany.com.kdoctor.R;
import thebrightcompany.com.kdoctor.view.home.HomeActivity;
import thebrightcompany.com.kdoctor.view.home.fragment.garageonmap.FindGarageFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class TroubleCodeFragment extends Fragment implements TroubleCodeView{

    public static final String TAG = TroubleCodeFragment.class.getSimpleName();
    private HomeActivity homeActivity;

    @BindView(R.id.listTroubleCode) RecyclerView listTroubleCode;
    @BindView(R.id.layout_find_garage) LinearLayout layout_find_garage;

    public TroubleCodeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_trouble_code, container, false);
        ButterKnife.bind(this, view);
        initView(view);
        return view;
    }

    private void initView(View view) {
        homeActivity.setTitle("Mã lỗi");
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
        homeActivity.setTitle("Mã lỗi");
    }

    @OnClick(R.id.layout_find_garage)
    public void processRedirectToGarageOnMapScreen(){
        //todo redirect to garage screen
        homeActivity.replaceFragment(new FindGarageFragment());
    }
}
