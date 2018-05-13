package thebrightcompany.com.kdoctor.view.loginmain.registerfragment;


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
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import thebrightcompany.com.kdoctor.R;
import thebrightcompany.com.kdoctor.utils.Utils;
import thebrightcompany.com.kdoctor.view.loginmain.LoginScreenActivity;
import thebrightcompany.com.kdoctor.view.loginmain.loginfragment.LoginFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment implements RegisterFragmentView{

    public static final String TAG = RegisterFragment.class.getSimpleName();

    @BindView(R.id.layout_register) LinearLayout layout_register;

    private LoginScreenActivity mActivity;

    private static Animation shakeAnimation;
    private static FragmentManager fragmentManager;

    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        ButterKnife.bind(this, view);
        initView(view);
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
        layout_register.startAnimation(shakeAnimation);
    }

    @Override
    public void onPasswordError(String msg) {
        layout_register.startAnimation(shakeAnimation);
    }

    @Override
    public void onRePasswordError(String msg) {
        layout_register.startAnimation(shakeAnimation);
    }

    @Override
    public void onPhoneError(String msg) {
        layout_register.startAnimation(shakeAnimation);
    }

    @Override
    public void onRegisterError(String msg) {
        layout_register.startAnimation(shakeAnimation);
    }

    @Override
    public void onRegisterSuccess(String msg) {
        // Replace signup frgament with animation
        /*fragmentManager
                .beginTransaction()
                .setCustomAnimations(R.animator.right_enter, R.animator.left_out)
                .replace(R.id.frameContainer, new LoginFragment(),
                        Utils.Login_Fragment).commit();*/
        replaceFragment(new LoginFragment());
    }

    @OnClick(R.id.btn_back)
    public void processBackLoginScreen(){
        //todo something
        // Replace signup frgament with animation
        /*fragmentManager
                .beginTransaction()
                .setCustomAnimations(R.animator.right_enter, R.animator.left_out)
                .replace(R.id.frameContainer, new LoginFragment(),
                        Utils.Login_Fragment).commit();*/
        replaceFragment(new LoginFragment());
    }

    @OnClick(R.id.btn_register)
    public void processRegister(){
        //todo something
        showMessage("Process register");
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
}
