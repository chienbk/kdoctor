package thebrightcompany.com.kdoctor.presenter.commentofgarage;

/**
 * Created by ChienNV on 11/24/16.
 */

public interface CommentOfGaragePresentor {

    void processGetCommentDetail(String token, String idOfGarage, String limit, int start, String rate);
}
