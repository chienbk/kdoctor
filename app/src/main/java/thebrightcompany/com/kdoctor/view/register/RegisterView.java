package thebrightcompany.com.kdoctor.view.register;

import java.io.File;

import thebrightcompany.com.kdoctor.view.BaseView;

public interface RegisterView extends BaseView{
    void onRegisterUser(String name, String msg, String phone, String pass, File avatar);
    void onRegisterUserSuccess(String msg);
    void onRegisterError(String msg);
    void onBackToLoginScreen();
    void onFullNameError(String msg);
    void onEmailError(String msg);
    void onPhoneError(String msg);
    void onPasswordError(String msg);
    void onAvatarError(String msg);
}
