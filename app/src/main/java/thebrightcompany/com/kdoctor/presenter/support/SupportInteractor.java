package thebrightcompany.com.kdoctor.presenter.support;

import thebrightcompany.com.kdoctor.model.support.Company;

/**
 * Created by ChienNV on 4/18/17.
 */

public interface SupportInteractor {

    interface OnSupportFinishedListener {

        void onCommonError(String msg);
        void onSupportError(String msg);
        void onSupportSuccess(Company company);
    }

    void processSupport();
}
