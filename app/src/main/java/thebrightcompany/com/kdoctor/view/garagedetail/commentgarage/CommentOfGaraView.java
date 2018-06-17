package thebrightcompany.com.kdoctor.view.garagedetail.commentgarage;

import thebrightcompany.com.kdoctor.model.commentgarage.DataOfComment;
import thebrightcompany.com.kdoctor.view.garagedetail.GarageDetailView;

public interface CommentOfGaraView extends GarageDetailView{

    void getCommentSuccess(String token, DataOfComment dataOfComment);
    void getCommentError(String msg, int status_code);
}
