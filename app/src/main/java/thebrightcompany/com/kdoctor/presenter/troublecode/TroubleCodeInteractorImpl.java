package thebrightcompany.com.kdoctor.presenter.troublecode;

import android.util.Log;

import com.android.volley.VolleyError;

import thebrightcompany.com.kdoctor.R;
import thebrightcompany.com.kdoctor.api.OnResponseListener;
import thebrightcompany.com.kdoctor.api.troublecode.TroubleCodeCallAPI;
import thebrightcompany.com.kdoctor.model.troublecode.TroubleCodeResponse;
import thebrightcompany.com.kdoctor.utils.Utils;
import thebrightcompany.com.kdoctor.view.home.fragment.troublecode.TroubleCodeView;

/**
 * Created by ChienNV on 11/24/16.
 */

public class TroubleCodeInteractorImpl implements TroubleCodeInteractor {

    public static final String TAG = TroubleCodeInteractorImpl.class.getSimpleName();

    private TroubleCodeView mView;
    private OnGetTroubleCodeFinishedListener mListener;

    public TroubleCodeInteractorImpl(TroubleCodeView mView, OnGetTroubleCodeFinishedListener mListener) {
        this.mView = mView;
        this.mListener = mListener;
    }

    @Override
    public void processGetTroubleCodeDetail(String token, String pCodes) {
        if (!Utils.isNetworkAvailable(mView.getContext())) {
            mListener.onCommonError(mView.getContext().getResources().getString(R.string.error_network));
            return;
        }
        //process call API
        processGetTroubleCode(token, pCodes);
    }

    private void processGetTroubleCode(String token, String pCodes) {
        OnGetTroubleCodeDetailListener listener = new OnGetTroubleCodeDetailListener();
        TroubleCodeCallAPI callAPI = new TroubleCodeCallAPI();
        callAPI.processGetTroubleCode(token, pCodes, listener);
    }


    private class OnGetTroubleCodeDetailListener extends OnResponseListener<TroubleCodeResponse>{
        @Override
        public void onErrorResponse(VolleyError error) {
            super.onErrorResponse(error);
            mListener.onGetTroubleCodeError(error.getMessage(), 0);

            Log.d(TAG, error.getMessage());
        }

        @Override
        public void onResponse(TroubleCodeResponse response) {
            super.onResponse(response);
            int status_code = response.getStatus_code();
            if (status_code == 0){
               mListener.onGetTroubleCodeSuccess(response.getDataOfTroubleCode().getToken(), response.getDataOfTroubleCode().getTroubleCodes());
            }else {
                mListener.onGetTroubleCodeError(response.getMessage(), status_code);
                Log.d(TAG, response.getMessage());
            }
        }
    }
}
