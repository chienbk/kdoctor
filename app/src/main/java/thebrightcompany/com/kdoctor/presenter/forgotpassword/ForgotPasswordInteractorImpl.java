package thebrightcompany.com.kdoctor.presenter.forgotpassword;

import com.android.volley.VolleyError;

import thebrightcompany.com.kdoctor.R;
import thebrightcompany.com.kdoctor.api.OnResponseListener;
import thebrightcompany.com.kdoctor.api.forgotpassword.ForgotPasswordCallAPI;
import thebrightcompany.com.kdoctor.model.forgotpassword.ForgotPasswordResponse;
import thebrightcompany.com.kdoctor.utils.Utils;
import thebrightcompany.com.kdoctor.view.loginmain.forgotpasswordfragment.ForgotPasswordFragmentView;

/**
 * Created by ChienNV on 11/24/16.
 */

public class ForgotPasswordInteractorImpl implements ForgotPasswordInteractor {

    private ForgotPasswordFragmentView mView;
    private OnFinishedListener mListener;

    public ForgotPasswordInteractorImpl(ForgotPasswordFragmentView mView, OnFinishedListener mListener) {
        this.mView = mView;
        this.mListener = mListener;
    }

    @Override
    public void processForgotPassword(String email) {

        if (Utils.isTextEmpty(email)) {
            mListener.onEmailError();
            mView.onEmailError(mView.getContext().getResources().getString(R.string.error_email_required));
            return;
        } else if (!(Utils.isValidlFormatEmail(email) || Utils.isValidUserFormat(email))) {
            mListener.onEmailError();
            mView.onEmailError(mView.getContext().getResources().getString(R.string.error_email_invalid));
            return;
        }

        if (!Utils.isNetworkAvailable(mView.getContext())) {
            mListener.onCommonError();
            mView.onCommonError(mView.getContext().getResources().getString(R.string.error_network));
            return;
        }
        //process call API
        processForgot(email);
    }

    private void processForgot(String email) {
        //todo something
        ForgotPasswordListener listener = new ForgotPasswordListener();
        ForgotPasswordCallAPI callAPI = new ForgotPasswordCallAPI();
        callAPI.processForgotPassword(email, listener);
    }

    private class ForgotPasswordListener extends OnResponseListener<ForgotPasswordResponse> {

        @Override
        public void onErrorResponse(VolleyError error) {
            mListener.onForgotPasswordError(error.getMessage());
            //mView.onApiMessage(error.toString());
            return;
        }

        @Override
        public void onResponse(ForgotPasswordResponse response) {
            int error_code = response.getStatus_code();
            if (error_code != 0) {
                mListener.onForgotPasswordError(response.getMessage());
                //mView.onApiMessage(response.getMessage().getMessage());
                return;
            } else{
                mListener.onForgotPasswordSuccess();
                //mView.onApiMessage(response.getMessage().getMessage());
            }
        }
    }
}
