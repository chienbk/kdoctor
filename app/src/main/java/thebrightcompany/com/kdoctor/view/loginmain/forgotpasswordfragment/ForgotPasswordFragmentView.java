package thebrightcompany.com.kdoctor.view.loginmain.forgotpasswordfragment;

import thebrightcompany.com.kdoctor.view.loginmain.LoginScreenView;

public interface ForgotPasswordFragmentView extends LoginScreenView {

    void onEmailError(String msg);
    void onGetPasswordSuccess();
    void onGetPasswordFail(String msg);
}
