package thebrightcompany.com.kdoctor.view.login;

import thebrightcompany.com.kdoctor.view.BaseView;

public interface LoginView extends BaseView{
    void onLoginError(String msg);
    void onLoginSuccess();
    void onEmailError(String msg);
    void onPasswordError(String msg);
    void onLoginWithGoogle();
    void onLoginWithFacebook();
    void onForgotPassword();
}
