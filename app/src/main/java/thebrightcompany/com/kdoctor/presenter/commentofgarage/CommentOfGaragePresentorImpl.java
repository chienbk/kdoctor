package thebrightcompany.com.kdoctor.presenter.commentofgarage;

import thebrightcompany.com.kdoctor.model.commentgarage.DataOfComment;
import thebrightcompany.com.kdoctor.view.garagedetail.commentgarage.CommentOfGaraView;

/**
 * Created by ChienNV on 11/24/16.
 */

public class CommentOfGaragePresentorImpl implements CommentOfGaragePresentor, CommentOfGarageInteractor.OnGetCommentOfGarageFinishedListener {

    private CommentOfGaraView mView;
    private CommentOfGarageInteractor interactor;

    public CommentOfGaragePresentorImpl(CommentOfGaraView mView) {
        this.mView = mView;
        interactor = new CommentOfGarageInteractorImpl(mView, this);
    }

    @Override
    public void onCommonError(String msg) {
        if (mView != null){
            mView.hideProgress();
            mView.onCommonError(msg);
        }
    }

    @Override
    public void onGetCommentSuccess(DataOfComment dataOfComment) {
        if (mView != null){
            mView.hideProgress();
            mView.getCommentSuccess(dataOfComment);
        }
    }

    @Override
    public void onGetCommentError(String msg, int status_code) {
        if (mView != null){
            mView.hideProgress();
            mView.getCommentError(msg, status_code);
        }
    }

    @Override
    public void processGetCommentDetail(String token, String idOfGarage, String limit, int start, String rate) {
        if (mView != null){
            mView.showProgress();
            interactor.processGetCommentDetail(token, idOfGarage, limit, start, rate);
        }
    }
}
