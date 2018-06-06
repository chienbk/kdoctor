package thebrightcompany.com.kdoctor.view.loginmain.loginfragment;

import thebrightcompany.com.kdoctor.view.loginmain.LoginScreenView;

public interface LoginFragmentView extends LoginScreenView{

    void onLoginError(String msg);
    void onLoginSuccess(String token);
    void onEmailError(String msg);
    void onPasswordError(String msg);
}
