package thebrightcompany.com.kdoctor.presenter.support;

import thebrightcompany.com.kdoctor.model.support.Company;
import thebrightcompany.com.kdoctor.view.home.fragment.support.SupportView;

/**
 * Created by ChienNV on 4/18/17.
 */

public class SupportPresenterImpl implements SupportPresenter, SupportInteractor.OnSupportFinishedListener{

    private SupportView mViews;
    private SupportInteractor interactor;

    public SupportPresenterImpl(SupportView mViews) {
        this.mViews = mViews;
        interactor = new SupportInteractorImpl(mViews, this);
    }

    @Override
    public void onCommonError(String msg) {
        if (mViews != null){
            mViews.hideProgress();
            mViews.onCommonError(msg);
        }
    }

    @Override
    public void onSupportError(String msg) {
        if (mViews != null){
            mViews.hideProgress();
            mViews.onInformationError(msg);
        }
    }

    @Override
    public void onSupportSuccess(Company company) {
        if (mViews != null){
            mViews.hideProgress();
            mViews.onInformationOfCompanySuccess(company);
        }
    }

    @Override
    public void processGetCompanyInformation() {
        if (mViews != null ){
            mViews.showProgress();
            interactor.processSupport();
        }
    }
}
