package thebrightcompany.com.kdoctor.view.home.fragment.diagnostic;

import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import thebrightcompany.com.kdoctor.model.diagnostic.Diagnostic;
import thebrightcompany.com.kdoctor.view.home.HomeActivity;

public class GetDiagnosticAsynTask extends AsyncTask<Void, Void, Boolean>{

    public static String TAG = GetDiagnosticAsynTask.class.getCanonicalName();

    private List<Diagnostic> mLists;
    private HomeActivity mActivity;
    private OnGetDataFinish mListener;

    public GetDiagnosticAsynTask(List<Diagnostic> mLists, HomeActivity mActivity, OnGetDataFinish mListener) {
        this.mLists = mLists;
        this.mActivity = mActivity;
        this.mListener = mListener;
    }



    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        if (mListener != null){
            mListener.onGetDataFinish(aBoolean);
        }
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        for (int i = 0; i < mLists.size(); i++){
            try {
                mActivity.sendDataToBLE(mLists.get(i).getCode());
                Log.d(TAG, "Send data: " + mLists.get(i).getCode());
                Thread.sleep(500);
            }catch (InterruptedException e){
                Thread.interrupted();
            }
        }
        return null;
    }
}
