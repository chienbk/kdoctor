package thebrightcompany.com.kdoctor.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;

import java.util.regex.Pattern;

public class Utils {

    public static final String ROOT_URL = "http://kdoctor.puma-soft.com/api";
    public static final String URL_FORGOT_PASSWORD = ROOT_URL + "/v1/forgotpass";
    public static final String URL_LOGIN = ROOT_URL + "/v1/login";

    //Email Validation pattern
    public static final String regEx = "\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}\\b";

    //Fragments Tags
    public static final String Login_Fragment = "Login_Fragment";
    public static final String SignUp_Fragment = "SignUp_Fragment";
    public static final String ForgotPassword_Fragment = "ForgotPassword_Fragment";

    /**
     *
     * @param data
     * @param format
     * @return
     */
    public static boolean isValidlFormat(String data, String format) {
        Pattern p = Pattern.compile(format);
        java.util.regex.Matcher m = p.matcher(data);

        return m.matches();
    }

    /**
     *
     * @param email
     * @return
     */
    public static boolean isValidlFormatEmail(String email) {

        String validEmailFormat = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        //String validEmailFormat = "^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+.[a-zA-Z0-9-.]+$";

        return isValidlFormat(email, validEmailFormat);

    }

    public static boolean isValidUserFormat(String user){

        String validUserFormat = "^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]*.[a-zA-Z0-9-.]+$";
        return isValidlFormat(user, validUserFormat);
    }

    /**
     *
     * @return
     */
    public static boolean isNetworkAvailable() {
        try {
            Process p1 = Runtime.getRuntime().exec("ping -c 1 www.google.com");
            int returnVal = p1.waitFor();
            boolean reachable = (returnVal==0);
            return reachable;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

    /**
     *
     * @param context
     * @return
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;

    }

    /**
     * The method use to check empty string
     * @param data
     * @return
     */
    public static boolean isTextEmpty(String data) {
        return TextUtils.isEmpty(data.trim());
    }
}
