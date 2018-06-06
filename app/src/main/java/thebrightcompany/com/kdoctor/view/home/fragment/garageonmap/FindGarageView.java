package thebrightcompany.com.kdoctor.view.home.fragment.garageonmap;

import java.util.List;

import thebrightcompany.com.kdoctor.model.garage.GarageOnMap;
import thebrightcompany.com.kdoctor.view.BaseView;

public interface FindGarageView extends BaseView{
    void onCommonError(String msg);
    void onSearchGarageSuccess(List<GarageOnMap> garageOnMaps);
    void onSearchGarageError(String msg);
    void onGetListGaragesSuccess(List<GarageOnMap> garageOnMaps);
    void onGetListGaragesError(String msg);
}
