package thebrightcompany.com.kdoctor.view.loginmain.registerfragment;

import thebrightcompany.com.kdoctor.view.loginmain.LoginScreenView;

public interface RegisterFragmentView extends LoginScreenView {
    void onEmailError(String msg);
    void onPasswordError(String msg);
    void onRePasswordError(String msg);
    void onPhoneError(String msg);
    void onRegisterError(String msg);
    void onRegisterSuccess(String msg);
    void onAvatarError(String msg);
}
