package thebrightcompany.com.kdoctor.presenter.detailofgarage;

import android.util.Log;

import com.android.volley.VolleyError;

import thebrightcompany.com.kdoctor.R;
import thebrightcompany.com.kdoctor.api.OnResponseListener;
import thebrightcompany.com.kdoctor.api.detailofgarage.DetailOfGarageCallAPI;
import thebrightcompany.com.kdoctor.model.garagedetail.GarageDetailResponse;
import thebrightcompany.com.kdoctor.utils.Utils;
import thebrightcompany.com.kdoctor.view.garagedetail.inforgarage.InformationOfGarageView;

/**
 * Created by ChienNV on 11/24/16.
 */

public class DetailOfGarageInteractorImpl implements DetailOfGarageInteractor {

    public static final String TAG = DetailOfGarageInteractorImpl.class.getSimpleName();

    private InformationOfGarageView mView;
    private OnGetDetailOfGarageFinishedListener mListener;

    public DetailOfGarageInteractorImpl(InformationOfGarageView mView, OnGetDetailOfGarageFinishedListener mListener) {
        this.mView = mView;
        this.mListener = mListener;
    }

    @Override
    public void processGetGarageDetail(String token, int idOfGarage) {
        if (!Utils.isNetworkAvailable(mView.getContext())) {
            mListener.onCommonError(mView.getContext().getResources().getString(R.string.error_network));
            return;
        }
        //process call API
        processGetGarage(token, idOfGarage);
    }

    private void processGetGarage(String token, int idOfGarage) {
        OnGetGarageDetailListener listener = new OnGetGarageDetailListener();
        DetailOfGarageCallAPI callAPI = new DetailOfGarageCallAPI();
        callAPI.processForgotPassword(token, String.valueOf(idOfGarage), listener);
    }


    private class OnGetGarageDetailListener extends OnResponseListener<GarageDetailResponse>{
        @Override
        public void onErrorResponse(VolleyError error) {
            super.onErrorResponse(error);
            mListener.onGetGarageError(error.getMessage(), 0);

            Log.d(TAG, error.getMessage());
        }

        @Override
        public void onResponse(GarageDetailResponse response) {
            super.onResponse(response);
            int status_code = response.getStatus_code();
            if (status_code == 0){
                mListener.onGetGarageSuccess(response.getDataOfGarage().getGarageDetail());
            }else {
                mListener.onGetGarageError(response.getMessage(), status_code);
                Log.d(TAG, response.getMessage());
            }
        }
    }
}
