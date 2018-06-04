package thebrightcompany.com.kdoctor.presenter.register;

import java.io.File;

/**
 * Created by ChienNV on 4/18/17.
 */

public interface RegisterInteractor {

    interface OnRegisterFinishedListener {

        void onAvatarError();

        void onPhoneError();

        void onPasswordError();

        void onRePasswordError();

        void onEmailError();

        void onFullNameError();

        void onCommonError();

        void onRegisterError(String msg);

        void onRegisterSuccess(String msg);
    }

    void processRegister(String full_name, String email, String phone, String password, String rePassword,
                      File avatar);
}
