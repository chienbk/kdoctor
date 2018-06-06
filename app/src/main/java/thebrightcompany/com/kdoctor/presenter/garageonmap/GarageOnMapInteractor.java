package thebrightcompany.com.kdoctor.presenter.garageonmap;

import java.util.List;

import thebrightcompany.com.kdoctor.model.garage.GarageOnMap;

/**
 * Created by ChienNV on 11/23/16.
 */

public interface GarageOnMapInteractor {

    interface OnGetGarageOnMapListener{

        void onGetGarageSuccess(List<GarageOnMap> garageOnMaps);

        void onGetGarageError(String msg);

        void onSearchGarageSuccess(List<GarageOnMap> garageOnMaps);

        void onSearchGarageError(String msg);

        void onCommonError(String msg);
    }

    void processGetGarageOnMap(String token, long lat, long lng, int distance);
    void processSearchGarageOnMap(String token, String key, int limit, int start);
}
