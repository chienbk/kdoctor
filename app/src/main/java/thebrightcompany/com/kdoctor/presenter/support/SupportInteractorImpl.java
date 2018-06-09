package thebrightcompany.com.kdoctor.presenter.support;

import com.android.volley.VolleyError;

import thebrightcompany.com.kdoctor.R;
import thebrightcompany.com.kdoctor.api.OnResponseListener;
import thebrightcompany.com.kdoctor.api.support.SupportCallAPI;
import thebrightcompany.com.kdoctor.model.support.SupportResponse;
import thebrightcompany.com.kdoctor.utils.Utils;
import thebrightcompany.com.kdoctor.view.home.fragment.support.SupportView;

/**
 * Created by ChienNV on 4/18/17.
 */

public class SupportInteractorImpl implements SupportInteractor {

    public static final String TAG = SupportInteractorImpl.class.getSimpleName();

    private SupportView mViews;
    private OnSupportFinishedListener mListener;

    public SupportInteractorImpl(SupportView mViews, OnSupportFinishedListener mListener) {
        this.mViews = mViews;
        this.mListener = mListener;
    }

    @Override
    public void processSupport() {

        //Validate network
        if (!Utils.isNetworkAvailable(mViews.getContext())) {
            mListener.onCommonError(mViews.getContext().getResources().getString(R.string.str_msg_network_fail));
            return;
        }

        //process call API
        processSupportRequest();
    }

    /**
     * The method use to call api
     */
    private void processSupportRequest() {
        GetCompanyListener listener = new GetCompanyListener();
        SupportCallAPI callAPI = new SupportCallAPI();
        callAPI.processForgotPassword(listener);
    }

    private class GetCompanyListener extends OnResponseListener<SupportResponse>{

        @Override
        public void onErrorResponse(VolleyError error) {
            super.onErrorResponse(error);
            mListener.onSupportError(error.getMessage());
        }

        @Override
        public void onResponse(SupportResponse response) {
            super.onResponse(response);
            int status_code = response.getStatus_code();
            if (status_code == 0){
                mListener.onSupportSuccess(response.getCompany());
            }else {
                mListener.onSupportError(response.getMessage());
            }
        }
    }

}
