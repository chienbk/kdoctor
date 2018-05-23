package thebrightcompany.com.kdoctor.view.home.fragment.garageonmap;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import butterknife.ButterKnife;
import thebrightcompany.com.kdoctor.R;
import thebrightcompany.com.kdoctor.view.home.HomeActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class FindGarageFragment extends Fragment implements FindGarageView, OnMapReadyCallback{

    public static final String TAG = FindGarageFragment.class.getSimpleName();
    private HomeActivity homeActivity;
    private GoogleMap mGoogleMap;

    public FindGarageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_find_garage, container, false);
        ButterKnife.bind(this, view);
        initMaps();
        initView(view);
        return view;
    }

    /**
     * The method use to init views
     * @param view
     */
    private void initView(View view) {
        homeActivity.setTitle("Tìm Garage");
    }

    /**
     * The method use to init google maps
     */
    private void initMaps() {
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.homeActivity = (HomeActivity) context;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mGoogleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    @Override
    public void showProgress() {
        homeActivity.showProgress();
    }

    @Override
    public void hideProgress() {
        homeActivity.hideProgress();
    }

    @Override
    public void showMessage(String message) {
        homeActivity.showMessage(message);
    }

    @Override
    public void onResume() {
        super.onResume();
        homeActivity.setTitle("Địa chỉ garage");
    }
}
