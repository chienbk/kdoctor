package thebrightcompany.com.kdoctor.presenter.tengarage;

import com.android.volley.VolleyError;

import thebrightcompany.com.kdoctor.R;
import thebrightcompany.com.kdoctor.api.OnResponseListener;
import thebrightcompany.com.kdoctor.api.tengarage.GetTenGarageCallAPI;
import thebrightcompany.com.kdoctor.model.garage.GarageOnMapResponse;
import thebrightcompany.com.kdoctor.utils.Utils;
import thebrightcompany.com.kdoctor.view.garagelist.GetTenGaragesView;
import thebrightcompany.com.kdoctor.view.home.fragment.garageonmap.FindGarageView;

/**
 * Created by ChienNV on 11/23/16.
 */

public class GetTenGarageInteractorImpl implements GetTenGarageInteractor {

    private GetTenGaragesView mViews;
    private OnGetTenGarageListener listener;

    public GetTenGarageInteractorImpl(GetTenGaragesView mViews, OnGetTenGarageListener listener) {
        this.mViews = mViews;
        this.listener = listener;
    }

    @Override
    public void processGetTenGarage(String token, double lat, double lng, int limit, String sortBy, String distance) {
        //Validate network
        if (!Utils.isNetworkAvailable(mViews.getContext())) {
            listener.onCommonError(mViews.getContext().getResources().getString(R.string.error_network));
            return;
        }

        processGetGarage(token, lat, lng, limit, sortBy, distance);
    }

    /**
     * The method use to get ten garage
     */
    private void processGetGarage(String token, double lat, double lng, int limit, String sortBy, String distance) {
        GetTenGarageListener listener = new GetTenGarageListener();
        GetTenGarageCallAPI callAPI = new GetTenGarageCallAPI();
        callAPI.processGetListGarage(token, String.valueOf(lat), String.valueOf(lng), String.valueOf(limit),
                sortBy, distance, listener);
    }

    /**
     * The method use to listener get ten garages
     */
    private class GetTenGarageListener extends OnResponseListener<GarageOnMapResponse>{
        @Override
        public void onErrorResponse(VolleyError error) {
            super.onErrorResponse(error);
            listener.onGetTenGarageError(error.getMessage());
        }

        @Override
        public void onResponse(GarageOnMapResponse response) {
            super.onResponse(response);
            int status_code = response.getStatus_code();
            if(status_code == 0){
                listener.onGetTenGarageSuccess(response.getDatasOnMap().getGarageOnMaps());
            }else {
                listener.onGetTenGarageError(response.getMessage());
            }
        }
    }
}
