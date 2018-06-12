package thebrightcompany.com.kdoctor.presenter.detailofgarage;

import thebrightcompany.com.kdoctor.model.garagedetail.GarageDetail;

/**
 * Created by ChienNV on 11/24/16.
 */

public interface DetailOfGarageInteractor {

    interface OnGetDetailOfGarageFinishedListener{

        void onCommonError(String msg);

        void onGetGarageSuccess(GarageDetail garageDetail);

        void onGetGarageError(String msg, int status_code);
    }

    void processgetGarageDetail(String email);
}
