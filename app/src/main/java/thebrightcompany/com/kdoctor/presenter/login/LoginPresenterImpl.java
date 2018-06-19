package thebrightcompany.com.kdoctor.presenter.login;


import thebrightcompany.com.kdoctor.model.login.Customer;
import thebrightcompany.com.kdoctor.view.loginmain.loginfragment.LoginFragmentView;

/**
 * Created by ChienNV on 4/17/17.
 */

public class LoginPresenterImpl implements LoginPresenter, LoginInteractor.OnLoginFinishedListener{

    private LoginFragmentView mViews;
    private LoginInteractor interactor;

    public LoginPresenterImpl(LoginFragmentView mViews) {
        this.mViews = mViews;
        interactor = new LoginInteractorImpl(mViews, this);
    }

    @Override
    public void processLogin(String email, String password, int type, String device_token, String third_token, String full_name) {
        if (mViews != null){
            mViews.showProgress();
            interactor.processLogin(email, password, type, device_token, third_token, full_name);
        }
    }

    @Override
    public void onEmailError() {
        if (mViews != null){
            mViews.hideProgress();
        }
    }

    @Override
    public void onPasswordError() {
        if (mViews != null){
            mViews.hideProgress();
        }
    }

    @Override
    public void onCommonError() {
        if (mViews != null){
            mViews.hideProgress();
        }
    }

    @Override
    public void onLoginError(String msg) {
        if (mViews != null){
            mViews.hideProgress();
        }
    }

    @Override
    public void onLoginSuccess(String token, Customer customer) {
        if (mViews != null){
            mViews.hideProgress();
            mViews.onLoginSuccess(token, customer);
        }
    }
}
