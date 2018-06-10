package thebrightcompany.com.kdoctor.presenter.garageonmap;

import com.android.volley.VolleyError;

import thebrightcompany.com.kdoctor.R;
import thebrightcompany.com.kdoctor.api.OnResponseListener;
import thebrightcompany.com.kdoctor.api.garageonmap.GetListGarageCallAPI;
import thebrightcompany.com.kdoctor.api.garageonmap.SearchGarageCallAPI;
import thebrightcompany.com.kdoctor.model.garage.GarageOnMapResponse;
import thebrightcompany.com.kdoctor.utils.Utils;
import thebrightcompany.com.kdoctor.view.home.fragment.garageonmap.FindGarageView;

/**
 * Created by ChienNV on 11/23/16.
 */

public class GarageOnMapInteractorImpl implements GarageOnMapInteractor {

    private FindGarageView mViews;
    private OnGetGarageOnMapListener listener;

    public GarageOnMapInteractorImpl(FindGarageView mViews, OnGetGarageOnMapListener listener) {
        this.mViews = mViews;
        this.listener = listener;
    }

    @Override
    public void processGetGarageOnMap(String token, double lat, double lng, int distance) {

        //Validate network
        if (!Utils.isNetworkAvailable(mViews.getContext())) {
            listener.onCommonError(mViews.getContext().getResources().getString(R.string.error_network));
            //mView.onLoginError(mViews.getContext().getResources().getString(R.string.error_network));
            return;
        }

        processGetGarage(token, lat, lng, distance);
    }

    /**
     * The method use to get list garage
     *
     * @param token
     * @param lat
     * @param lng
     * @param distance
     */
    private void processGetGarage(String token, double lat, double lng, int distance) {

        //todo something
        GetGarageListener listener = new GetGarageListener();
        GetListGarageCallAPI callAPI = new GetListGarageCallAPI();
        callAPI.processGetListGarage(token, String.valueOf(lat), String.valueOf(lng), String.valueOf(distance), listener);
    }

    /**
     * The method use to listener get garage
     */
    private class GetGarageListener extends OnResponseListener<GarageOnMapResponse> {
        @Override
        public void onErrorResponse(VolleyError error) {
            super.onErrorResponse(error);
            listener.onGetGarageError(0, error.getMessage());
        }

        @Override
        public void onResponse(GarageOnMapResponse response) {
            super.onResponse(response);

            int status_code = response.getStatus_code();
            if (status_code == 0){
                listener.onGetGarageSuccess(response.getDatasOnMap().getGarageOnMaps());
            }else {
                listener.onGetGarageError(response.getStatus_code(), response.getMessage());
            }
        }
    }

    @Override
    public void processSearchGarageOnMap(String token, String key, int limit, int start) {
        //Validate network
        if (!Utils.isNetworkAvailable(mViews.getContext())) {
            listener.onCommonError(mViews.getContext().getResources().getString(R.string.error_network));
            //mView.onLoginError(mViews.getContext().getResources().getString(R.string.error_network));
            return;
        }

        processSearchGarage(token, key, limit, start);
    }

    /**
     * The method use to search garage on maps
     *
     * @param token
     * @param key
     * @param limit
     * @param start
     */
    private void processSearchGarage(String token, String key, int limit, int start) {
        //todo something
        SearchGarageListener listener = new SearchGarageListener();
        SearchGarageCallAPI callAPI = new SearchGarageCallAPI();
        callAPI.processGetListGarage(token, key, String.valueOf(limit), String.valueOf(start), listener);
    }

    private class SearchGarageListener extends OnResponseListener<GarageOnMapResponse>{
        @Override
        public void onErrorResponse(VolleyError error) {
            super.onErrorResponse(error);
            listener.onSearchGarageError(0, error.getMessage());
        }

        @Override
        public void onResponse(GarageOnMapResponse response) {
            super.onResponse(response);
            int status_code = response.getStatus_code();
            if (status_code == 0){
                //todo something
                listener.onSearchGarageSuccess(response.getDatasOnMap().getGarageOnMaps());
            }else {
                listener.onSearchGarageError(response.getStatus_code(), response.getMessage());
            }
        }
    }
}
