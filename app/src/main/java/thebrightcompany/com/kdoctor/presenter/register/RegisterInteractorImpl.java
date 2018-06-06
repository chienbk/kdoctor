package thebrightcompany.com.kdoctor.presenter.register;

import android.util.Log;

import com.android.volley.VolleyError;

import java.io.File;

import thebrightcompany.com.kdoctor.api.OnResponseListener;
import thebrightcompany.com.kdoctor.api.register.RegisterCallAPI;
import thebrightcompany.com.kdoctor.model.register.RegisterResponse;
import thebrightcompany.com.kdoctor.utils.Utils;
import thebrightcompany.com.kdoctor.view.loginmain.registerfragment.RegisterFragmentView;
/**
 * Created by ChienNV on 4/18/17.
 */

public class RegisterInteractorImpl implements RegisterInteractor {

    public static final String TAG = RegisterInteractorImpl.class.getSimpleName();

    private RegisterFragmentView mViews;
    private OnRegisterFinishedListener mListener;

    public RegisterInteractorImpl(RegisterFragmentView mViews, OnRegisterFinishedListener mListener) {
        this.mViews = mViews;
        this.mListener = mListener;
    }

    @Override
    public void processRegister(String full_name, String email, String phone, String password, String rePassword, File avatar) {

        //Process validate form
        /*if (avatar == null) {
            mListener.onAvatarError();
            //mViews.setAvatarError(mViews.getContext().getResources().getString(R.string.err_register_avatar));
            return;
        }*/

        if (Utils.isTextEmpty(phone)) {
            mListener.onPhoneError();
            //mViews.setPhoneError(mViews.getContext().getResources().getString(R.string.error_driver_phone));
            return;
        }

        if (Utils.isTextEmpty(password) || password.length() < 6) {
            mListener.onPasswordError();
            //mViews.setPasswordError(mViews.getContext().getResources().getString(R.string.error_driver_password));
            return;
        }

        if (Utils.isTextEmpty(rePassword) || !password.equals(rePassword)) {
            mListener.onRePasswordError();
            //mViews.setRePasswordError(mViews.getContext().getResources().getString(R.string.error_driver_repassword));
            return;
        }

        if (Utils.isTextEmpty(full_name)) {
            mListener.onFullNameError();
            //mViews.setFullNameError(mViews.getContext().getResources().getString(R.string.error_driver_driver_name));
            return;
        }

        //Validate network
        if (!Utils.isNetworkAvailable(mViews.getContext())) {
            mListener.onCommonError();
            //mViews.setCommonError(mViews.getContext().getResources().getString(R.string.str_msg_network_fail));
            return;
        }

        //process call API
        processRegisterDriver(full_name, email, phone, password, avatar);
    }

    /**
     * The method use to process register
     *
     * @param full_name
     * @param email
     * @param phone
     * @param password
     * @param avatar
     */
    private void processRegisterDriver(String full_name, String email, String phone, String password, File avatar) {
        //todo something
        RegisterListener listener = new RegisterListener();
        RegisterCallAPI request = new RegisterCallAPI();
        request.processForgotPassword(full_name, email, phone, password, listener);
    }


    private class RegisterListener extends OnResponseListener<RegisterResponse> {
        @Override
        public void onErrorResponse(VolleyError error) {
            super.onErrorResponse(error);
            mListener.onRegisterError(error.getMessage());
            Log.d(TAG, error.getMessage());
        }

        @Override
        public void onResponse(RegisterResponse response) {
            super.onResponse(response);
            int status_code = response.getStatus_code();
            if (status_code == 0){
                mListener.onRegisterSuccess(response.getMessage());
            }else {
                mListener.onRegisterError(response.getMessage());
            }
        }
    }
    /*private void processRegisterDriver(String full_name, String email, String phone, String password, File avatar) {

        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        asyncHttpClient.setConnectTimeout(30000);
        RequestParams params = new RequestParams();

//        Map<String, String> params = new HashMap<String, String>();

        params.put("email", email);
        params.put("phone_number", phone);
        params.put("pass", password);
        params.put("full_name", full_name);
        try {
            if (avatar != null) {
                params.put("image_avatar", avatar);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        asyncHttpClient.post(Utils.URL_REGISTER, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                boolean success = false;
                try {
                    success = response.getBoolean("success");
                    if (success) {
                        mListener.onRegisterSuccess("Login success!");
                        //mViews.setApiMessage(response.getString("message"));

                    } else{
                        mListener.onRegisterError("");
                        //mViews.setApiMessage(response.getString("message"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                //mListener.onApiError();
                //mViews.setApiMessage(mViews.getContext().getResources().getString(R.string.msg_error_registed));
                Log.d("Register: ", errorResponse.toString());
            }
        });
    }*/
}
