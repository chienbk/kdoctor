package thebrightcompany.com.kdoctor.view.garagedetail.commentgarage;

import thebrightcompany.com.kdoctor.model.commentgarage.DataOfComment;
import thebrightcompany.com.kdoctor.view.garagedetail.GarageDetailView;

public interface CommentOfGaraView extends GarageDetailView{

    void getCommentSuccess(DataOfComment dataOfComment);
    void getCommentError(String msg);
}
