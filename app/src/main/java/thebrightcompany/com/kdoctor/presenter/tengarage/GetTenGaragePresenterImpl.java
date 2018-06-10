package thebrightcompany.com.kdoctor.presenter.tengarage;


import java.util.List;

import thebrightcompany.com.kdoctor.model.garage.GarageOnMap;
import thebrightcompany.com.kdoctor.view.garagelist.GetTenGaragesView;

/**
 * Created by ChienNV on 4/17/17.
 */

public class GetTenGaragePresenterImpl implements GetTenGaragePresenter, GetTenGarageInteractor.OnGetTenGarageListener{

    private GetTenGaragesView mView;
    private GetTenGarageInteractor interactor;

    public GetTenGaragePresenterImpl(GetTenGaragesView mView) {
        this.mView = mView;
        this.interactor = new GetTenGarageInteractorImpl(mView, this);
    }

    @Override
    public void onGetTenGarageSuccess(List<GarageOnMap> garageOnMaps) {
        if (mView != null){
            mView.hideProgress();
            mView.onGetTenGaragesSuccess(garageOnMaps);
        }
    }

    @Override
    public void onGetTenGarageError(int status_code, String msg) {
        if (mView != null){
            mView.hideProgress();
            mView.onGetTenGaragesError(status_code, msg);
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
    public void processGetTenGarage(String token, double lat, double lng, int limit, String sortBy, String distance) {
        if (mView != null){
            mView.showProgress();
            interactor.processGetTenGarage(token, lat, lng, limit, sortBy, distance);
        }
    }
}
