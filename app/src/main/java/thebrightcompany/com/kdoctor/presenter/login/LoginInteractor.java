package thebrightcompany.com.kdoctor.presenter.login;

/**
 * Created by ChienNV on 11/23/16.
 */

public interface LoginInteractor {

    interface OnLoginFinishedListener{

        void onEmailError();

        void onPasswordError();

        void onCommonError();

        void onLoginError(String msg);

        void onLoginSuccess(String device_token);
    }

    void processLogin(String email, String password, int type, String device_token, String third_token, String full_name);
}
