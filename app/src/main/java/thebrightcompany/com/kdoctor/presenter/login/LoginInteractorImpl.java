package thebrightcompany.com.kdoctor.presenter.login;

import android.util.Log;

import com.android.volley.VolleyError;

import thebrightcompany.com.kdoctor.R;
import thebrightcompany.com.kdoctor.api.OnResponseListener;
import thebrightcompany.com.kdoctor.api.login.LoginCallAPI;
import thebrightcompany.com.kdoctor.model.login.LoginResponse;
import thebrightcompany.com.kdoctor.utils.Utils;
import thebrightcompany.com.kdoctor.view.loginmain.loginfragment.LoginFragmentView;

/**
 * Created by ChienNV on 11/23/16.
 */

public class LoginInteractorImpl implements LoginInteractor {

    private LoginFragmentView mView;
    private OnLoginFinishedListener mListener;

    public LoginInteractorImpl(LoginFragmentView mViews, OnLoginFinishedListener mListener) {

        this.mView = mViews;
        this.mListener = mListener;
    }

    @Override
    public void processLogin(String email, String password, int type, String device_token, String third_token, String full_name) {
        //Validate UI
        if (Utils.isTextEmpty(email)) {
            mListener.onEmailError();
            mView.onEmailError(mView.getContext().getResources().getString(R.string.error_email_required));
            return;
        } else if (!(Utils.isValidlFormatEmail(email) || Utils.isValidUserFormat(email))) {
            mListener.onEmailError();
            mView.onEmailError(mView.getContext().getResources().getString(R.string.error_email_invalid));
            return;
        }

        if (Utils.isTextEmpty(password)) {
            mListener.onPasswordError();
            mView.onPasswordError(mView.getContext().getResources().getString(R.string.error_password_required));
            return;
        } else if (!Utils.isGreaterThan(password, 6)) {
            mListener.onPasswordError();
            mView.onPasswordError(mView.getContext().getResources().getString(R.string.error_password_invalid));
            return;
        }
        if (type != Utils.LOGIN_NORMAL && third_token.isEmpty()){
            mListener.onLoginError("Login with FB of Google fail!");
            return;
        }

        //Validate network
        if (!Utils.isNetworkAvailable(mView.getContext())) {
            mListener.onCommonError();
            mView.onLoginError(mView.getContext().getResources().getString(R.string.error_network));
            return;
        }

        //process call API
        processCallAPI(email, password, type, device_token, third_token, full_name);
    }

    private void processCallAPI(String email, String password, int type, String device_token, String third_token, String full_name) {
        LoginListener listener = new LoginListener();
        LoginCallAPI callAPI = new LoginCallAPI();
        callAPI.processForgotPassword(email, password, String.valueOf(type), device_token, third_token, full_name, listener);

    }

    private class LoginListener extends OnResponseListener<LoginResponse> {
        @Override
        public void onErrorResponse(VolleyError error) {
            mListener.onLoginError(error.toString());
            Log.d("Error: ", error.toString());
            return;
        }

        @Override
        public void onResponse(LoginResponse response) {
            int status_code = response.getStatus_code();

            if (status_code == 0){
                Utils.APP_TOKEN = response.getData().getToken();
                mListener.onLoginSuccess(response.getData().getToken());
            }else {
                mListener.onLoginError(response.getMessage());
                return;
            }
        }
    }
}
