package thebrightcompany.com.kdoctor.view.home.fragment.troublecode;

import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import thebrightcompany.com.kdoctor.view.home.HomeActivity;

public class GetTroubleCodeAsynTask extends AsyncTask<Void, Void, Boolean>{

    public static String TAG = GetTroubleCodeAsynTask.class.getCanonicalName();

    private HomeActivity activity;
    private List<String> mLists;

    public GetTroubleCodeAsynTask(HomeActivity activity, List<String> mLists) {
        this.activity = activity;
        this.mLists = mLists;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        for (int i = 0; i < mLists.size(); i++){
            try {
                activity.sendDataToBLE(mLists.get(i));
                Log.d(TAG, "Send data: " + mLists.get(i));
                Thread.sleep(500);
            }catch (InterruptedException e){
                Thread.interrupted();
            }
        }
        return null;
    }
}
