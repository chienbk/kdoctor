package thebrightcompany.com.kdoctor.view.loginmain.forgotpasswordfragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.OnClick;
import thebrightcompany.com.kdoctor.R;
import thebrightcompany.com.kdoctor.view.loginmain.LoginScreenActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class ForgotPasswordFragment extends Fragment implements ForgotPasswordFragmentView{

    public static final String TAG = ForgotPasswordFragment.class.getSimpleName();

    private LoginScreenActivity mActivity;

    public ForgotPasswordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_forgot_password, container, false);
        ButterKnife.bind(this, view);
        initView(view);
        return view;
    }

    private void initView(View view) {
        //todo something
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = (LoginScreenActivity) context;
    }

    @Override
    public void showProgress() {
        mActivity.showProgress();
    }

    @Override
    public void hideProgress() {
        mActivity.hideProgress();
    }

    @Override
    public void showMessage(String message) {
        mActivity.showMessage(message);
    }

    @Override
    public void onEmailError(String msg) {

    }

    @Override
    public void onGetPasswordSuccess(String msg) {

    }

    @Override
    public void onGetPasswordFail(String msg) {

    }

    @OnClick(R.id.btn_back)
    public void processBack(){
        //todo something
    }

    @OnClick(R.id.btn_getPassword)
    public void processGetPassword(){
        //todo something
    }
}
