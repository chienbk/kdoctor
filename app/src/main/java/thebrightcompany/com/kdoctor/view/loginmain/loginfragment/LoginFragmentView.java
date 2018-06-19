package thebrightcompany.com.kdoctor.view.loginmain.loginfragment;

import thebrightcompany.com.kdoctor.model.login.Customer;
import thebrightcompany.com.kdoctor.view.loginmain.LoginScreenView;

public interface LoginFragmentView extends LoginScreenView{

    void onLoginError(String msg);
    void onLoginSuccess(String token, Customer customer);
    void onEmailError(String msg);
    void onPasswordError(String msg);
}
