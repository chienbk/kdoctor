package thebrightcompany.com.kdoctor.view.garagelist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import thebrightcompany.com.kdoctor.R;
import thebrightcompany.com.kdoctor.model.garage.Garage;

/**
 * Created by CongVC on 5/25/2018.
 */

public class GarageListActivity extends AppCompatActivity {
    private RecyclerView rvGarageList;
    private AdapterGarageList adapterGarageList;
    private ArrayList<Garage> garageArrayList = new ArrayList();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_garage_list);
        rvGarageList = (RecyclerView)findViewById(R.id.rvGarageList);
        rvGarageList.setHasFixedSize(true);
        rvGarageList.setLayoutManager(new LinearLayoutManager(this));
        adapterGarageList = new AdapterGarageList(this,garageArrayList);
        rvGarageList.setAdapter(adapterGarageList);

        // get data from server
        getGarageListFromServer();
    }
    public void getGarageListFromServer(){
//        garageArrayList = ...;
        adapterGarageList.notifyDataSetChanged();
    }
}
