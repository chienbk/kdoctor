package thebrightcompany.com.kdoctor.view.garagelist;

import java.util.List;

import thebrightcompany.com.kdoctor.model.garage.GarageOnMap;
import thebrightcompany.com.kdoctor.view.BaseView;

public interface GetTenGaragesView extends BaseView{

    void onGetTenGaragesSuccess(String token, List<GarageOnMap> garageOnMaps);
    void onGetTenGaragesError(int status_code, String msg);
    void onCommonError(String msg);
}
