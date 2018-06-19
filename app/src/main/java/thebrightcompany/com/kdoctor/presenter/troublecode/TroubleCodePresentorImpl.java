package thebrightcompany.com.kdoctor.presenter.troublecode;

import java.util.List;

import thebrightcompany.com.kdoctor.model.troublecode.TroubleCode;
import thebrightcompany.com.kdoctor.view.home.fragment.troublecode.TroubleCodeView;

/**
 * Created by ChienNV on 11/24/16.
 */

public class TroubleCodePresentorImpl implements TroubleCodePresentor, TroubleCodeInteractor.OnGetTroubleCodeFinishedListener {

    private TroubleCodeView mView;
    private TroubleCodeInteractor interactor;

    public TroubleCodePresentorImpl(TroubleCodeView mView) {
        this.mView = mView;
        interactor = new TroubleCodeInteractorImpl(mView, this);
    }

    @Override
    public void onCommonError(String msg) {
        if (mView != null){
            mView.hideProgress();
            mView.onCommonError(msg);
        }
    }

    @Override
    public void onGetTroubleCodeSuccess(String token, List<TroubleCode> troubleCodes) {
        if (mView != null){
            mView.hideProgress();
            mView.onGetTroubleCodeSuccess(token, troubleCodes);
        }
    }

    @Override
    public void onGetTroubleCodeError(String msg, int status_code) {

        if (mView != null){
            mView.hideProgress();
            mView.onGetTroubleCodeError(status_code, msg);
        }
    }

    @Override
    public void processGetTroubleCodeDetail(String token, String pCodes) {

        if (mView != null){
            mView.showProgress();
            interactor.processGetTroubleCodeDetail(token, pCodes);
        }
    }
}
