package thebrightcompany.com.kdoctor.view.home.fragment.troublecode;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import thebrightcompany.com.kdoctor.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TroubleCodeFragment extends Fragment {


    public TroubleCodeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_trouble_code, container, false);
    }

}
