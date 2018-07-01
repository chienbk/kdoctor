package thebrightcompany.com.kdoctor.view.loginmain.forgotpasswordfragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import thebrightcompany.com.kdoctor.R;
import thebrightcompany.com.kdoctor.presenter.forgotpassword.ForgotPasswordPresentor;
import thebrightcompany.com.kdoctor.presenter.forgotpassword.ForgotPasswordPresentorImpl;
import thebrightcompany.com.kdoctor.view.loginmain.LoginScreenActivity;
import thebrightcompany.com.kdoctor.view.loginmain.loginfragment.LoginFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class ForgotPasswordFragment extends Fragment implements ForgotPasswordFragmentView{

    public static final String TAG = ForgotPasswordFragment.class.getSimpleName();

    @BindView(R.id.layout_forgot_password) LinearLayout layout_forgot_password;
    @BindView(R.id.email) EditText txt_email;

    private LoginScreenActivity mActivity;

    private static Animation shakeAnimation;
    private static FragmentManager fragmentManager;
    private ForgotPasswordPresentor presentor;

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
        presentor = new ForgotPasswordPresentorImpl(this);
        return view;
    }

    private void initView(View view) {
        //todo something
        fragmentManager = getActivity().getSupportFragmentManager();
        // Load ShakeAnimation
        shakeAnimation = AnimationUtils.loadAnimation(getActivity(),
                R.anim.shake);
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
        layout_forgot_password.startAnimation(shakeAnimation);
        txt_email.setError(msg);
        txt_email.requestFocus();
    }

    @Override
    public void onGetPasswordSuccess() {
        replaceFragment(new LoginFragment());
    }

    @Override
    public void onGetPasswordFail(String msg) {
        //layout_forgot_password.startAnimation(shakeAnimation);
        showMessage(msg);
    }

    @OnClick(R.id.btn_back)
    public void processBack(){
        //todo something
        // Replace signup frgament with animation
        replaceFragment(new LoginFragment());

    }

    @OnClick(R.id.btn_getPassword)
    public void processGetPassword(){
        //todo something
        //showMessage("Process forgot password");
        String email = txt_email.getText().toString();
        presentor.processForgotPassword(email);
    }

    private void replaceFragment(Fragment fragment) {
        if (fragment != null) {
            FragmentManager fragmentManager = mActivity.getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(R.animator.right_enter, R.animator.left_out);
            fragmentTransaction.replace(R.id.frameContainer, fragment);
            fragmentTransaction.addToBackStack(fragment.getClass().getSimpleName());
            fragmentTransaction.commitAllowingStateLoss();
        }
    }

    @Override
    public void onCommonError(String msg) {
        showMessage(msg);
    }
}
