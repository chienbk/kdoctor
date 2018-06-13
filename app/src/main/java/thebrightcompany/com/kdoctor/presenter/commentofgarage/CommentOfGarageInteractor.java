package thebrightcompany.com.kdoctor.presenter.commentofgarage;

import thebrightcompany.com.kdoctor.model.commentgarage.DataOfComment;

/**
 * Created by ChienNV on 11/24/16.
 */

public interface CommentOfGarageInteractor {

    interface OnGetCommentOfGarageFinishedListener{

        void onCommonError(String msg);

        void onGetCommentSuccess(DataOfComment dataOfComment);

        void onGetCommentError(String msg, int status_code);
    }

    void processGetCommentDetail(String token, String idOfGarage, String limit, int start, String rate);
}
