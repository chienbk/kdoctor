package thebrightcompany.com.kdoctor.presenter.garageonmap;

import java.util.List;

import thebrightcompany.com.kdoctor.model.garage.GarageOnMap;

/**
 * Created by ChienNV on 11/23/16.
 */

public interface GarageOnMapInteractor {

    interface OnGetGarageOnMapListener{

        void onGetGarageSuccess(String token, List<GarageOnMap> garageOnMaps);

        void onGetGarageError(int status_code, String msg);

        void onSearchGarageSuccess(String token, List<GarageOnMap> garageOnMaps);

        void onSearchGarageError(int status_code, String msg);

        void onCommonError(String msg);
    }

    void processGetGarageOnMap(String token, double lat, double lng, int distance);
    void processSearchGarageOnMap(String token, String key, int limit, int start);
}
