package thebrightcompany.com.kdoctor.presenter.forgotpassword;

/**
 * Created by ChienNV on 11/24/16.
 */

public interface ForgotPasswordInteractor {

    interface OnFinishedListener{

        void onEmailError();

        void onCommonError();

        void onForgotPasswordError(String msg);

        void onForgotPasswordSuccess();
    }

    void processForgotPassword(String email);
}
