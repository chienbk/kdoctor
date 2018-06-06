package thebrightcompany.com.kdoctor.view.garagedetail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import thebrightcompany.com.kdoctor.R;
import thebrightcompany.com.kdoctor.model.garage.Garage;

/**
 * Created by CongVC on 6/5/2018.
 */

public class FragmentInfo extends Fragment {
    private ViewPager mSlideImage;
    private TextView tvGarageName, tvAddress, tvPhoneNumber, tvMail, tvServiceContent;
    private Garage garage;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_garage_detail, container, false);
        tvGarageName = (TextView) view.findViewById(R.id.tvGarageName);
        tvAddress = (TextView) view.findViewById(R.id.tvAddress);
        tvPhoneNumber = (TextView) view.findViewById(R.id.tvPhoneNumber);
        tvMail = (TextView) view.findViewById(R.id.tvMail);
        tvServiceContent = (TextView) view.findViewById(R.id.tvServiceContent);
        show();
        return view;
    }

    private void show(){
        tvGarageName.setText(garage.getName());
        tvAddress.setText(garage.getAddress());
        tvPhoneNumber.setText(garage.getPhoneNumber());
        tvMail.setText(garage.getMail());
        tvServiceContent.setText(garage.getServiceContent());

    }

    public void setGarage(Garage garage) {
        this.garage = garage;
    }

    public Garage getGarage() {
        return this.garage;
    }
}
