package thebrightcompany.com.kdoctor.api.commentofgarage;

import thebrightcompany.com.kdoctor.App;
import thebrightcompany.com.kdoctor.api.OnResponseListener;
import thebrightcompany.com.kdoctor.model.commentgarage.CommentResponse;

/**
 * Created by ChienNV on 10/25/16.
 */

public class CommentOfGarageCallAPI {

    public void processGetCommentOfGarage(String token, String idOfGarage, String limit, String start, String rating,
                                      OnResponseListener<CommentResponse> listener){
        CommentOfGarageRequest request = new CommentOfGarageRequest(listener, idOfGarage);
        request.setToken(token);
        request.setLimit(limit);
        request.setStart(start);
        request.setRating(rating);

        App.addRequest(request, "Comment Garage");
    }
}
