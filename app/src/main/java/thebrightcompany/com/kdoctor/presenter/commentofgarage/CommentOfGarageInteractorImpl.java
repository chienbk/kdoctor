package thebrightcompany.com.kdoctor.presenter.commentofgarage;

import android.util.Log;

import com.android.volley.VolleyError;

import thebrightcompany.com.kdoctor.R;
import thebrightcompany.com.kdoctor.api.OnResponseListener;
import thebrightcompany.com.kdoctor.api.commentofgarage.CommentOfGarageCallAPI;
import thebrightcompany.com.kdoctor.model.commentgarage.CommentResponse;
import thebrightcompany.com.kdoctor.utils.Utils;
import thebrightcompany.com.kdoctor.view.garagedetail.commentgarage.CommentOfGaraView;

/**
 * Created by ChienNV on 11/24/16.
 */

public class CommentOfGarageInteractorImpl implements CommentOfGarageInteractor {

    public static final String TAG = CommentOfGarageInteractorImpl.class.getSimpleName();

    private CommentOfGaraView mView;
    private OnGetCommentOfGarageFinishedListener mListener;

    public CommentOfGarageInteractorImpl(CommentOfGaraView mView, OnGetCommentOfGarageFinishedListener mListener) {
        this.mView = mView;
        this.mListener = mListener;
    }

    @Override
    public void processGetCommentDetail(String token, String idOfGarage, String limit, int start, String rate) {
        if (!Utils.isNetworkAvailable(mView.getContext())) {
            mListener.onCommonError(mView.getContext().getResources().getString(R.string.error_network));
            return;
        }
        //process call API
        processGetComment(token, idOfGarage, limit, start, rate);
    }

    private void processGetComment(String token, String idOfGarage, String limit, int start, String rate) {
        OnGetCommentGarageListener listener = new OnGetCommentGarageListener();
        CommentOfGarageCallAPI callAPI = new CommentOfGarageCallAPI();
        callAPI.processGetCommentOfGarage(token, idOfGarage, limit, String.valueOf(start), rate, listener);
    }

    private class OnGetCommentGarageListener extends OnResponseListener<CommentResponse>{
        @Override
        public void onErrorResponse(VolleyError error) {
            super.onErrorResponse(error);
            mListener.onGetCommentError(error.getMessage(), 0);
            Log.d(TAG, error.getMessage());
        }

        @Override
        public void onResponse(CommentResponse response) {
            super.onResponse(response);
            int status_code = response.getStatus_code();
            if (status_code == 0){
                mListener.onGetCommentSuccess(response.getDataOfComment().getToken(), response.getDataOfComment());
            }else {
                mListener.onGetCommentError(response.getMessage(), status_code);
                Log.d(TAG, response.getMessage());
            }
        }
    }
}
