package thebrightcompany.com.kdoctor.presenter.troublecode;

import java.util.List;

import thebrightcompany.com.kdoctor.model.troublecode.TroubleCode;

/**
 * Created by ChienNV on 11/24/16.
 */

public interface TroubleCodeInteractor {

    interface OnGetTroubleCodeFinishedListener{

        void onCommonError(String msg);

        void onGetTroubleCodeSuccess(String token, List<TroubleCode> troubleCodes);

        void onGetTroubleCodeError(String msg, int status_code);
    }

    void processGetTroubleCodeDetail(String token, String pCodes);
}
