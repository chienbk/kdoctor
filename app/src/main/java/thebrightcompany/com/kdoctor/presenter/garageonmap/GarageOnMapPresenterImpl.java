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
    public void onGetGarageSuccess(String token, List<GarageOnMap> garageOnMaps) {
        if (mView != null){
            mView.hideProgress();
            mView.onGetListGaragesSuccess(token, garageOnMaps);
        }
    }

    @Override
    public void onGetGarageError(int status_code, String msg) {
        if (mView != null){
            mView.hideProgress();
            mView.onGetListGaragesError(status_code, msg);
        }
    }

    @Override
    public void onSearchGarageSuccess(String token, List<GarageOnMap> garageOnMaps) {
        if (mView != null){
            mView.hideProgress();
            mView.onSearchGarageSuccess(token, garageOnMaps);
        }
    }

    @Override
    public void onSearchGarageError(int status_code, String msg) {
        if (mView != null){
            mView.hideProgress();
            mView.onSearchGarageError(status_code, msg);
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
    public void onCreateOrderSuccess(String msg) {
        if (mView != null) {
            mView.hideProgress();
            mView.onCreateOrderSuccess(msg);
        }
    }

    @Override
    public void onCreateOrderError(String msg) {
        if (mView != null) {
            mView.hideProgress();
            mView.onCreateOrderError(msg);
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

    @Override
    public void processCreateOrder(String garage_id, String name, String phone, String email, String typeOfCar, String licenseOfCar, String note, String troublecode, String token, String lat, String lng) {
        if (mView != null) {
            mView.showProgress();
            interactor.processCreateOrder(garage_id, name, phone, email, typeOfCar, licenseOfCar, note, troublecode, token, lat, lng);
        }
    }

    @Override
    public void processCreateOrderWithLocation(String garage_id, String name, String phone, String email, String typeOfCar, String licenseOfCar, String note, String troublecode, String token, String location) {
        if (mView != null) {
            mView.showProgress();
            interactor.processCreateOrderWithLocation(garage_id, name, phone, email, typeOfCar, licenseOfCar, note, troublecode, token, location);
        }
    }
}
