package thebrightcompany.com.kdoctor.view.garagedetail.commentgarage;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import thebrightcompany.com.kdoctor.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CommentOfGaraFragment extends Fragment {


    public CommentOfGaraFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_comment_of_gara, container, false);
    }

}
