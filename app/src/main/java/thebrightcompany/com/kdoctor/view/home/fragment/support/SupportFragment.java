package thebrightcompany.com.kdoctor.view.home.fragment.support;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import thebrightcompany.com.kdoctor.R;
import thebrightcompany.com.kdoctor.model.support.Company;
import thebrightcompany.com.kdoctor.presenter.support.SupportPresenter;
import thebrightcompany.com.kdoctor.presenter.support.SupportPresenterImpl;
import thebrightcompany.com.kdoctor.view.home.HomeActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class SupportFragment extends Fragment implements SupportView{

    public static final String TAG = SupportFragment.class.getSimpleName();
    private HomeActivity homeActivity;

    @BindView(R.id.txt_nameOfCompany) TextView txt_nameOfCompany;
    @BindView(R.id.txt_address_support) TextView txt_address_support;
    @BindView(R.id.layout_call) LinearLayout layout_call;
    @BindView(R.id.txt_hot_line_support) TextView txt_hot_line_support;
    @BindView(R.id.txt_email_support) TextView txt_email_support;
    @BindView(R.id.txt_web) TextView txt_web;

    private SupportPresenter presenter;

    public SupportFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_support, container, false);
        ButterKnife.bind(this, view);
        initView(view);
        return view;
    }

    /**
     * The method use to init view
     * @param view
     */
    private void initView(View view) {
        homeActivity.setTitle("Hỗ trợ");
        presenter = new SupportPresenterImpl(this);
        presenter.processGetCompanyInformation();
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
        homeActivity.setTitle("Hỗ trợ");
    }

    @OnClick(R.id.layout_call)
    public void processCall(){
        //todo something
        Intent callSupport = new Intent(Intent.ACTION_CALL, Uri
                .parse("tel:" + txt_hot_line_support.getText().toString()));
        startActivity(callSupport);
    }

    @Override
    public void onInformationOfCompanySuccess(Company companyInfomation) {
        if (companyInfomation != null){
            try {
                txt_nameOfCompany.setText(companyInfomation.getCompany());
                txt_address_support.setText(companyInfomation.getAddress());
                txt_hot_line_support.setText(companyInfomation.getPhone());
                txt_email_support.setText(companyInfomation.getEmail());
                txt_web.setText(companyInfomation.getEmail());
            }catch (Exception e){
                Log.d(TAG, e.toString());
            }
        }
    }

    @Override
    public void onInformationError(int status_code, String msg) {
        showMessage(msg);
    }

    @Override
    public void onCommonError(String msg) {
        showMessage(msg);
    }

    @Nullable
    @Override
    public Context getContext() {
        return super.getContext();
    }
}
