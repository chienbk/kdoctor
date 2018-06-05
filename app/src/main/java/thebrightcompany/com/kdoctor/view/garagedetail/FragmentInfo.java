package thebrightcompany.com.kdoctor.view.garagedetail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import thebrightcompany.com.kdoctor.R;

/**
 * Created by CongVC on 6/5/2018.
 */

public class FragmentInfo extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_garage_detail, container, false);
        return view;
    }
}
