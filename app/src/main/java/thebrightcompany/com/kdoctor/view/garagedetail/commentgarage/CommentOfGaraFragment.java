package thebrightcompany.com.kdoctor.view.garagedetail.commentgarage;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import thebrightcompany.com.kdoctor.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CommentOfGaraFragment extends Fragment {

    public static final String TAG = CommentOfGaraFragment.class.getSimpleName();

    private static final String ARG_ID_GARAGE = "ID_OF_GARAGE";


    private int idOfGarage;

    public CommentOfGaraFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param id
     * @return
     */
    public static CommentOfGaraFragment newInstance(String id) {
        CommentOfGaraFragment fragment = new CommentOfGaraFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ID_GARAGE, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idOfGarage = Integer.parseInt(getArguments().getString(ARG_ID_GARAGE));
            Log.d(TAG, "idOfGarage: " + idOfGarage);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_comment_of_gara, container, false);
    }

}
