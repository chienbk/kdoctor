package thebrightcompany.com.kdoctor.presenter.garageonmap;


import java.util.List;

import thebrightcompany.com.kdoctor.model.garage.GarageOnMap;
import thebrightcompany.com.kdoctor.view.home.fragment.garageonmap.FindGarageView;

/**
 * Created by ChienNV on 4/17/17.
 */

public class GarageOnMapPresenterImpl implements GarageOnMapPresenter, GarageOnMapInteractor.OnGetGarageOnMapListener{

    private FindGarageView mView;
    private GarageOnMapInteractor interactor;

    public GarageOnMapPresenterImpl(FindGarageView mView) {
        this.mView = mView;
        this.interactor = new GarageOnMapInteractorImpl(mView, this);
    }

    @Override
    public void onGetGarageSuccess(List<GarageOnMap> garageOnMaps) {
        if (mView != null){
            mView.hideProgress();
            mView.onGetListGaragesSuccess(garageOnMaps);
        }
    }

    @Override
    public void onGetGarageError(String msg) {
        if (mView != null){
            mView.hideProgress();
            mView.onGetListGaragesError(msg);
        }
    }

    @Override
    public void onSearchGarageSuccess(List<GarageOnMap> garageOnMaps) {
        if (mView != null){
            mView.hideProgress();
            mView.onSearchGarageSuccess(garageOnMaps);
        }
    }

    @Override
    public void onSearchGarageError(String msg) {
        if (mView != null){
            mView.hideProgress();
            mView.onSearchGarageError(msg);
        }
    }

    @Override
    public void onCommonError(String msg) {
        if (mView != null){
            mView.hideProgress();
            mView.onCommonError(msg);
        }
    }

    @Override
    public void processGetGarageOnMap(String token, double lat, double lng, int distance) {
        if (mView != null){
            mView.showProgress();
            interactor.processGetGarageOnMap(token, lat, lng, distance);
        }
    }

    @Override
    public void processSearchGarageOnMap(String token, String key, int limit, int start) {
        if (mView != null){
            mView.showProgress();
            interactor.processSearchGarageOnMap(token, key, limit, start);
        }
    }
}
