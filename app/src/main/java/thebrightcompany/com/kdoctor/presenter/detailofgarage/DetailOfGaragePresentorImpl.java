package thebrightcompany.com.kdoctor.presenter.detailofgarage;

import thebrightcompany.com.kdoctor.model.garagedetail.GarageDetail;
import thebrightcompany.com.kdoctor.presenter.forgotpassword.ForgotPasswordInteractor;
import thebrightcompany.com.kdoctor.presenter.forgotpassword.ForgotPasswordInteractorImpl;
import thebrightcompany.com.kdoctor.presenter.forgotpassword.ForgotPasswordPresentor;
import thebrightcompany.com.kdoctor.view.garagedetail.inforgarage.InformationOfGarageView;
import thebrightcompany.com.kdoctor.view.loginmain.forgotpasswordfragment.ForgotPasswordFragment;

/**
 * Created by ChienNV on 11/24/16.
 */

public class DetailOfGaragePresentorImpl implements DetailOfGaragePresentor, DetailOfGarageInteractor.OnGetDetailOfGarageFinishedListener {

    private InformationOfGarageView mView;
    private DetailOfGarageInteractor interactor;

    public DetailOfGaragePresentorImpl(InformationOfGarageView mView) {
        this.mView = mView;
        interactor = new DetailOfGarageInteractorImpl(mView, this);
    }

    @Override
    public void onCommonError(String msg) {
        if (mView != null){
            mView.hideProgress();
            mView.onCommonError(msg);
        }
    }

    @Override
    public void onGetGarageSuccess(GarageDetail garageDetail) {
        if (mView != null){
            mView.hideProgress();
            mView.getGaraDetailSuccess(garageDetail);
        }
    }

    @Override
    public void onGetGarageError(String msg, int status_code) {
        if (mView != null){
            mView.hideProgress();
            mView.getGaraDetailError(msg, status_code);
        }
    }

    @Override
    public void processGetGarageDetail(String token, int idOfGarage) {
        if (mView != null){
            mView.showProgress();
            interactor.processGetGarageDetail(token, idOfGarage);
        }
    }
}
