package thebrightcompany.com.kdoctor.presenter.commentofgarage;

import thebrightcompany.com.kdoctor.model.garagedetail.GarageDetail;

/**
 * Created by ChienNV on 11/24/16.
 */

public interface CommentOfGarageInteractor {

    interface OnGetCommentOfGarageFinishedListener{

        void onCommonError(String msg);

        void onGetCommentSuccess(GarageDetail garageDetail);

        void onGetCommentError(String msg, int status_code);
    }

    void processgetCommentDetail(String email);
}
