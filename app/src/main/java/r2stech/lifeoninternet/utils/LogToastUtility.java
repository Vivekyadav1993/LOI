package r2stech.lifeoninternet.utils;
/**
 * Class Name       :  <b>LogToastUtility.java<b/>
 * Purpose          :  LogToastUtility is java class contain logs and toast which are used in though out the project.
 * Developed By     :  <b>@radhu_android</b>
 * Created Date     :  <b>5aug2017</b>
 * <p/>
 * Modified Reason  :  <b></b>
 * Modified By      :  <b></b>
 * Modified Date    :  <b></b>
 * <p/>
 */

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class LogToastUtility {
    public final static String TAG = "LOI";

    public static void LI(String msg1, String msg2) {
        // TODO to display messages in project
        // msg1 is caller(class or function ) and msg2 is message
        Log.i(TAG, msg1 + " " + msg2);
    }

    public static void LE(String msg1, String msg2) {
        // TODO to display error during exception in project
        // msg1 is caller(class or function ) and msg2 is message
        Log.e(TAG, msg1 + " " + msg2);
    }

    public static void TIS(Context context, String msg1, String msg2) {
        // TODO to display Toast messages and log
        // msg1 is caller(class or function ) and msg2 is message
        Log.i(TAG, "Toast : " + msg1 + " " + msg2);
        Toast.makeText(context, msg2, Toast.LENGTH_SHORT).show();
    }

    public static void TIL(Context context, String msg1, String msg2) {
        // TODO to display error during exception in project
        // msg1 is caller(class or function ) and msg2 is message
        Log.i(TAG, "Toast : " + msg1 + " " + msg2);
        Toast.makeText(context, msg2, Toast.LENGTH_LONG).show();
    }

    public static void TES(Context context, String msg1, String msg2) {
        // TODO to display error during exception in project
        // msg1 is caller(class or function ) and msg2 is message
        Log.e(TAG, "Toast : " + msg1 + " " + msg2);
        Toast.makeText(context, msg2, Toast.LENGTH_SHORT).show();
    }

    public static void TEL(Context context, String msg1, String msg2) {
        // TODO to display error during exception in project
        // msg1 is caller(class or function ) and msg2 is message
        Log.e(TAG, "Toast : " + msg1 + " " + msg2);
        Toast.makeText(context, msg2, Toast.LENGTH_LONG).show();
    }

    /*//TODO Unused
    public static void SBS(View view, String msg1, String msg2) {
        // TODO to display error during exception in project
        // msg1 is caller(class or function ) and msg2 is message
        Log.e(TAG, "Toast : " + msg1 + " " + msg2);
        Snackbar.make(view, msg2, Snackbar.LENGTH_SHORT).setAction("Action", null).show();
    }

    public static void SBL(View view) {
        // TODO to display error during exception in project
        // msg1 is caller(class or function ) and msg2 is message

        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }*/


    public static void TOAST_L(Context context, String msg2) {
        // TODO to display error during exception in project
        // msg1 is caller(class or function ) and msg2 is message
        Log.e("Toast", "Toast : " + msg2);
        Toast.makeText(context, msg2, Toast.LENGTH_SHORT).show();
    }
    public static void TOAST_S(Context context, String msg2) {
        // TODO to display error during exception in project
        // msg1 is caller(class or function ) and msg2 is message
        Log.e("Toast", "Toast : " + msg2);
        Toast.makeText(context, msg2, Toast.LENGTH_LONG).show();
    }
}
