package thebrightcompany.com.kdoctor.presenter.forgotpassword;

import thebrightcompany.com.kdoctor.view.loginmain.forgotpasswordfragment.ForgotPasswordFragmentView;

/**
 * Created by ChienNV on 11/24/16.
 */

public class ForgotPasswordPresentorImpl implements ForgotPasswordPresentor, ForgotPasswordInteractor.OnFinishedListener {

    private ForgotPasswordFragmentView mView;
    private ForgotPasswordInteractor interactor;

    public ForgotPasswordPresentorImpl(ForgotPasswordFragmentView mView) {
        this.mView = mView;
        interactor = new ForgotPasswordInteractorImpl(mView, this);
    }

    @Override
    public void onEmailError() {
        if (mView != null) {
            mView.hideProgress();
        }
    }

    @Override
    public void onCommonError() {
        if (mView != null) {
            mView.hideProgress();
        }
    }

    @Override
    public void onForgotPasswordError(String msg) {
        if (mView != null){
            mView.hideProgress();
            mView.onGetPasswordFail(msg);
        }
    }

    @Override
    public void onForgotPasswordSuccess() {
        if (mView != null){
            mView.hideProgress();
            mView.onGetPasswordSuccess();
        }
    }

    @Override
    public void processForgotPassword(String email) {
        if (mView != null) {
            mView.showProgress();
            interactor.processForgotPassword(email);
        }
    }
}
