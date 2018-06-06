package thebrightcompany.com.kdoctor.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import thebrightcompany.com.kdoctor.model.diagnostic.Diagnostic;

public class Utils {

    public static final String ROOT_URL = "http://kdoctor.puma-soft.com/api/";
    public static final String URL_FORGOT_PASSWORD = ROOT_URL + "v1/forgotpass";
    public static final String URL_LOGIN = ROOT_URL + "v1/login";
    public static final String URL_REGISTER = ROOT_URL + "v1/register";
    public static final String URL_TROUBLE_CODE = ROOT_URL + "v1/trouble-code";
    public static final String URL_GET_LIST_GARAGE = ROOT_URL + "v1/list_gara_on_map";
    public static final String URL_SEARCH_GARAGE_ON_MAP = ROOT_URL + "v1/garages/search";

    public static final int LOGIN_NORMAL = 0;
    public static final int LOGIN_FACEBOOK = 1;
    public static final int LOGIN_GOOGLE = 2;

    public static String APP_TOKEN = "";
    public static String FCM_TOKEN = "";

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

    /**
     * The method use to check lengh of data
     * @param data
     * @param count
     * @return
     */
    public static boolean isGreaterThan(String data, int count) {
        return (data.trim().length() >= count);
    }

    /**
     * The method use to get list diagnostic
     * @return mList
     */
    public static List<Diagnostic> getListDiagnostic(){
        List<Diagnostic> mList = new ArrayList<>();
        Diagnostic diagnostic1 = new Diagnostic("Vận tốc phương tiện", "01 0D", "410D", "0", "km/h", "0", "255", "Min value -> Max value : 0 -> 255km/h");
        Diagnostic diagnostic2 = new Diagnostic("Vòng tua RPM", "01 0C", "410C", "0", "rpm", "0", "16383", "Min value -> Max value : 0 -> 16383.75rpm");
        Diagnostic diagnostic3 = new Diagnostic("Nhiệt độ làm mát động cơ", "01 05", "4105", "0", "°C", "-40", "215", "Min value -> Max value : -40 -> 215°C");
        Diagnostic diagnostic4 = new Diagnostic("Calculated engine load", "01 04", "4104", "0", "%", "0", "100","Min value -> Max value : 0 -> 100%");
        Diagnostic diagnostic5 = new Diagnostic("Nhiên liệu đầu vào", "01 2F", "412F", "0", "%","0", "100", "Min value -> Max value : 0 -> 100%");
        Diagnostic diagnostic6 = new Diagnostic("Áp suất nhiên liệu", "01 0A", "410A", "0", "kPa","0", "765", "Min value -> Max value : 0 ->765kPa");
        Diagnostic diagnostic7 = new Diagnostic("Nhiệt độ không khí vào", "01 0F", "410F", "0", "°C", "-40", "215", "Min value -> Max value : -40 -> 215°C");
        Diagnostic diagnostic8 = new Diagnostic("Nồng độ % Ethanol", "01 52", "4152", "0", "%", "0", "100", "Min value -> Max value : 0 -> 100%");
        Diagnostic diagnostic9 = new Diagnostic("Nhiệt độ dầu động cơ", "01 5C", "415C", "0", "°C", "-40", "210", "Min value -> Max value : -40 -> 210°C");
        Diagnostic diagnostic10 = new Diagnostic("Short term fuel trim—Bank 1", "01 06", "4106", "0", "%", "-100", "99", "Min value -> Max value : -100 -> 99.2%");
        Diagnostic diagnostic11 = new Diagnostic("Long term fuel trim—Bank 1", "01 07", "4107", "0", "%", "-100", "99", "Min value -> Max value : -100 -> 99.2%");
        Diagnostic diagnostic12 = new Diagnostic("Catalyst Temperature: Bank 1, Sensor 1", "01 3C", "413C", "0", "°C", "-40", "6513", "Min value -> Max value : -40 -> 6513.5°C");
        Diagnostic diagnostic13 = new Diagnostic("Catalyst Temperature: Bank 2, Sensor 1", "01 3D", "413D", "0", "°C", "-40", "6513", "Min value -> Max value : -40 -> 6513.5°C");
        Diagnostic diagnostic14 = new Diagnostic("Ambient air temperature", "01 46", "4146", "0", "°C", "-40", "215", "Min value -> Max value : -40 -> 215°C");
        Diagnostic diagnostic15 = new Diagnostic("Absolute throttle position B", "01 47", "4147", "0", "%","0", "100", "Min value -> Max value : 0 -> 100%");


        mList.add(diagnostic1);
        mList.add(diagnostic2);
        mList.add(diagnostic3);
        mList.add(diagnostic4);
        mList.add(diagnostic5);
        mList.add(diagnostic6);
        mList.add(diagnostic7);
        mList.add(diagnostic8);
        mList.add(diagnostic9);
        mList.add(diagnostic10);
        mList.add(diagnostic11);
        mList.add(diagnostic12);
        mList.add(diagnostic13);
        mList.add(diagnostic14);
        mList.add(diagnostic15);


        return mList;
    }


    public static final String TAG = Utils.class.getSimpleName();

    /**
     *
     * @param hex
     * @return
     */
    public static int convertHexToDecimal(String hex) {

        return Integer.parseInt(hex, 16);
    }

    /**
     *
     * @param hex
     * @return
     */
    public static float convertHexToFloat(String hex) {
        Long i = Long.parseLong(hex, 16);
        return Float.intBitsToFloat(i.intValue());
    }

    /**
     * The method used to convert integer to vehicle speed
     * @param data
     * @return speed (km/h)
     * Data bytes returned : 1
     * Fomula : A
     */
    public static int convertIntegerToVehicleSpeed(String data) {
        return convertHexToDecimal(data);
    }

    /**
     * The method used to convert integer to vehicle speed
     * @param data
     * @return rpm
     * Data bytes returned : 2
     * Fomula : (256*A + B)/4
     */
    public static int convertIntegerToEngineRPM(String data){
        return convertHexToDecimal(data)/4;
    }

    /**
     * The method used to convert integer to temperature
     * @param data
     * @return temperature °C
     * Data bytes returned : 1
     * Fomula : A-40
     */
    public static int convertIntegerToCoolantTemperature(String data) {
        return convertHexToDecimal(data) - Contains.CONS_TEMPERATURE;
    }

    /**
     * The method used to convert integer to calculated engine load
     * @param data
     * @return  percent %
     * Data bytes returned : 1
     * Fomula : A*100/255
     */
    public static int convertIntegerToEngineLoad(String data) {
        return convertHexToDecimal(data)*100/255 ;
    }

    /**
     * The method used to convert integer to fuel tank
     * @param data
     * @return percent (%)
     * Data bytes returned : 1
     * Fomula : A*100/255
     *
     */
    public static int convertIntegerToFuelTank(String data) {
        return convertHexToDecimal(data)*100/255;
    }

    /**
     * The method used to convert integer to Fuel pressure
     * @param data
     * @return Pascals (kPa)
     * Data bytes returned : 1
     * Fomula : A*3
     */
    public static int convertIntegerToFuelPressure(String data) {
        return convertHexToDecimal(data)*3;
    }

    /**
     * The method used to convert integer to Intake air temperature
     * @param data
     * @return Temperature (°C)
     * Data bytes returned : 1
     * Fomula : A-40
     */
    public static int convertIntegerToIntakeAirTemperature(String data) {
        return convertHexToDecimal(data) - Contains.CONS_TEMPERATURE;
    }

    /**
     * The method used to convert integer to Ethanol fuel
     * @param data
     * @return percent (%)
     * Data bytes returned : 1
     * Fomula : A*100/255
     */
    public static int convertIntegerToEthanolFuel(String data) {
        return convertHexToDecimal(data)*100/255;
    }

    /**
     * The method used to convert integer to Engine oil temperature
     * @param data
     * @return temperature (°C)
     * Data bytes returned : 1
     * Fomula : A-40
     */
    public static int convertIntegerToEngineOilTemperature(String data) {
        return convertHexToDecimal(data )- Contains.CONS_TEMPERATURE;
    }

    /**
     * The method used to convert integer to Short term fuel trim
     * @param data
     * @return percent (%)
     * Data bytes returned : 1
     * Fomula : A*100/128 - 100
     */
    public static int convertIntegerToTermFuelTrim(String data) {
        return (convertHexToDecimal(data)*100/128)-100;
    }

    //Short term fuel trim—Bank 1
    public static int convertIntegerToShortTermFuelTrim_Bank_1(String data) {
        return convertIntegerToTermFuelTrim(data);
    }

    // Long term fuel trim—Bank 1
    public static int convertIntegerToLongTermFuelTrim_Bank_1(String data) {
        return convertIntegerToTermFuelTrim(data);
    }

    /**
     * The method used to convert integer to Catalyst Temperature
     * @param data
     * @return temperature (°C)
     * Data bytes returned : 2
     * Fomula : (A*256 + B)/10 - 40
     */
    public static int convertIntegerToCatalystTemperature(String data) {
        return (convertHexToDecimal(data)/10)-40;
    }

    /**
     *
     * @param data
     * @return
     */
    public static int convertToTotalCErrorCode(String data){
        data = data.substring(0, 2);
        Log.d(TAG, "convertToTotalCErrorCode: " + data);
        if (convertHexToDecimal(data) > 80){
            return convertHexToDecimal(data) - 128;
        }else return convertHexToDecimal(data);
    }

    // Catalyst Temperature: Bank 1, Sensor 1
    public static int convertIntegerToCatalystTemperature_Bank1_Sensor1(String data) {
        return convertIntegerToCatalystTemperature(data);
    }

    // 	Catalyst Temperature: Bank 2, Sensor 1
    public static int convertIntegerToCatalystTemperature_Bank2_Sensor1(String data) {
        return convertIntegerToCatalystTemperature(data);
    }

    /**
     * The method used to convert integer to Ambient air temperature
     * @param data
     * @return temperature (°C)
     * Data bytes returned : 1
     * Fomula : A-40
     */
    public static int convertIntegerToAmbientAirTemperature(String data) {
        return convertHexToDecimal(data)-40;
    }

    /**
     * The method used to convert integer to Absolute throttle
     * @param data
     * @return percent (%)
     * Data bytes returned : 1
     * Fomula : A*100/255
     */
    public static int convertIntegerToAbsoluteThrottle(String data) {
        return convertHexToDecimal(data)*100/255;
    }

    // Absolute throttle position B
    public static int convertIntegerToAbsoluteThrottle_Position_B(String data) {
        return convertIntegerToAbsoluteThrottle(data);
    }

    public static String convertDataReceiveToString(String data) {
        String newData = data.replaceAll(">", "");
        newData = newData.replaceAll("\\s+","");
        return newData.substring(4, newData.length());
    }

    public static String convertDataReceiveToStringTroubleCode(String data) {
        String newData = data.replaceAll(">", "");
        newData = newData.replaceAll("\\s+","");
        return newData.substring(2, newData.length());
    }
}
