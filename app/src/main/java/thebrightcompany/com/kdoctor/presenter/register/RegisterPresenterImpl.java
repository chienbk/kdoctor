package thebrightcompany.com.kdoctor.presenter.register;


import java.io.File;

import thebrightcompany.com.kdoctor.R;
import thebrightcompany.com.kdoctor.view.loginmain.registerfragment.RegisterFragmentView;
import thebrightcompany.com.kdoctor.view.register.RegisterView;

/**
 * Created by ChienNV on 4/18/17.
 */

public class RegisterPresenterImpl implements RegisterPresenter, RegisterInteractor.OnRegisterFinishedListener {

    private RegisterFragmentView mViews;
    private RegisterInteractor interactor;

    public RegisterPresenterImpl(RegisterFragmentView mViews) {
        this.mViews = mViews;
        interactor = new RegisterInteractorImpl(mViews, this);
    }

    @Override
    public void onAvatarError() {
        if (mViews != null) {
            mViews.hideProgress();
            mViews.onAvatarError(mViews.getContext().getResources().getString(R.string.err_register_avatar));
        }
    }

    @Override
    public void onPhoneError() {
        if (mViews != null) {
            mViews.hideProgress();
            mViews.onPhoneError(mViews.getContext().getResources().getString(R.string.error_driver_phone));
        }
    }


    @Override
    public void onPasswordError() {
        if (mViews != null) {
            mViews.hideProgress();
            mViews.onPasswordError(mViews.getContext().getResources().getString(R.string.error_driver_password));
        }
    }

    @Override
    public void onRePasswordError() {
        if (mViews != null) {
            mViews.hideProgress();
            mViews.onRePasswordError(mViews.getContext().getResources().getString(R.string.error_driver_repassword));
        }
    }

    @Override
    public void onEmailError() {
        if (mViews != null) {
            mViews.hideProgress();
        }
    }

    @Override
    public void onFullNameError() {
        if (mViews != null) {
            mViews.hideProgress();

        }
    }

    @Override
    public void onCommonError() {
        if (mViews != null) {
            mViews.hideProgress();
            mViews.onCommonError(mViews.getContext().getResources().getString(R.string.str_msg_network_fail));
        }
    }

    @Override
    public void onRegisterError(String msg) {
        if (mViews != null) {
            mViews.hideProgress();
            mViews.onRegisterError(msg);
        }
    }

    @Override
    public void onRegisterSuccess(String msg) {
        if (mViews != null) {
            mViews.hideProgress();
            mViews.onRegisterSuccess(msg);
            //mViews.redirectToLoginView();
        }
    }

    @Override
    public void processRegister(String full_name, String email, String phone, String password, String rePassword,
                                File avatar) {
        if (mViews != null) {
            mViews.showProgress();
            interactor.processRegister(full_name, email, phone, password, rePassword, avatar);
        }
    }
}
