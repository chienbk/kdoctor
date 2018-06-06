package thebrightcompany.com.kdoctor.view.garagedetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import thebrightcompany.com.kdoctor.R;

/**
 * Created by CongVC on 6/5/2018.
 */

public class ActivityGarageDetail extends AppCompatActivity {
    private ViewPager vpDetail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_garage_detail);
        vpDetail = (ViewPager) findViewById(R.id.vpDetail);
    }
}
