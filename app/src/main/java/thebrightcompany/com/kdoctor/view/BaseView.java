package thebrightcompany.com.kdoctor.view;

import android.content.Context;

/**
 * Created by ChienNV on 11/23/16.
 */

public interface BaseView {

    Context getContext();

    void showProgress();

    void hideProgress();

    void showMessage(String message);
}
