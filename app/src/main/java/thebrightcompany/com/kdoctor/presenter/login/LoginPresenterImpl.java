package com.globmove.chuhang.presenter.login;

import com.globmove.chuhang.fragment.LoginView;

/**
 * Created by ChienNV on 4/17/17.
 */

public class LoginPresenterImpl implements LoginPresenter, LoginInteractor.OnFinishedListener{

    private LoginView mViews;
    private LoginInteractor interactor;

    public LoginPresenterImpl(LoginView mViews) {
        this.mViews = mViews;
        interactor = new LoginInteractorImpl(mViews, this);
    }

    @Override
    public void processLogin(String phone, String password, String registration_id) {
        if (mViews != null){
            mViews.showProgress();
            interactor.processLogin(phone, password, registration_id);
        }
    }

    @Override
    public void onPhonelError() {
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
    public void onApiError() {
        if (mViews != null){
            mViews.hideProgress();
        }
    }

    @Override
    public void onSuccess() {
        if (mViews != null){
            //mViews.registerService();
            mViews.hideProgress();
            mViews.redirectToHomeView();
        }
    }

    @Override
    public void onActiveUser() {
        if(mViews != null){
            mViews.hideProgress();
            mViews.redirectToActiveView();
        }
    }
}
