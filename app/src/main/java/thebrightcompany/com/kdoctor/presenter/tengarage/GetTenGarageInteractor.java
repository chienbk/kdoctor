package thebrightcompany.com.kdoctor.presenter.tengarage;

import java.util.List;

import thebrightcompany.com.kdoctor.model.garage.GarageOnMap;

/**
 * Created by ChienNV on 11/23/16.
 */

public interface GetTenGarageInteractor {

    interface OnGetTenGarageListener{

        void onGetTenGarageSuccess(List<GarageOnMap> garageOnMaps);

        void onGetTenGarageError(int status_code, String msg);

        void onCommonError(String msg);
    }

    void processGetTenGarage(String token, double lat, double lng, int limit, String sortBy, String distance);
}
