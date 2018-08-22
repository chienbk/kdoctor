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

        void onCreateOrderSuccess(String msg);

        void onCreateOrderError(String msg);
    }

    void processGetGarageOnMap(String token, double lat, double lng, int distance);
    void processSearchGarageOnMap(String token, String key, int limit, int start);
    void processCreateOrder(String garage_id, String name, String phone, String email,
                            String typeOfCar, String licenseOfCar, String note, String troubleCode,
                            String token, String lat, String lng);
    void processCreateOrderWithLocation(String garage_id, String name, String phone, String email,
                            String typeOfCar, String licenseOfCar, String note, String troubleCode,
                            String token, String location);
}
