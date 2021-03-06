package thebrightcompany.com.kdoctor.api.commentofgarage;


import android.util.Log;

import com.google.gson.reflect.TypeToken;

import thebrightcompany.com.kdoctor.api.OnResponseListener;
import thebrightcompany.com.kdoctor.api.base.BasePostRequest;
import thebrightcompany.com.kdoctor.model.commentgarage.CommentResponse;
import thebrightcompany.com.kdoctor.utils.Utils;

/**
 * Created by ChienNV on 10/25/16.
 */

public class CommentOfGarageRequest extends BasePostRequest<CommentResponse> {

    public static final String TAG = CommentOfGarageRequest.class.getSimpleName();

    public CommentOfGarageRequest(OnResponseListener<CommentResponse> listener, String idOfGarage) {
        super(String.format(Utils.URL_GARAGE_COMMENT, idOfGarage), new TypeToken<CommentResponse>() {
        }.getType(), listener);
        Log.d(TAG, "URL_comment: " + String.format(Utils.URL_GARAGE_COMMENT, idOfGarage));
    }

    public void setToken(String token) {
        setParam("token", token);
    }
    public void setLimit(String limit){
        setParam("limit", limit);
    }
    public void setStart(String start){
        setParam("start", start);
    }
    public void setRating(String rating){
        setParam("rating", rating);
    }
}
