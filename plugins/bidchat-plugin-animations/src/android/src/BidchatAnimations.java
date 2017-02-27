package com.bidchat.BidchatAnimations;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;

/**
 * This class echoes a string called from JavaScript.
 */
public class BidchatAnimations extends CordovaPlugin implements PopMenuItemClickListener {

    private String popMenuCallback;
    private Context context;

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {

        if (action.equals("showShareMenu")) {
            String callback = args.getString(0);
            this.showShareMenu(context, callback);
            return true;
        }

        if (action.equals("showCountdownTimer")) {
            int timerStartValue = args.getInt(0);
            String callback = args.getString(1);
            this.showCountdownTimer(timerStartValue, callback);  
            return true;
        }

        return false;
    }

    public void showCountdownTimer(int timerStartValue, String callback) {

        this.webView.sendJavascript(callback + "( );");
    }

    public void showShareMenu(Context context, String callback) {

        this.context = this.cordova.getActivity();
        this.popMenuCallback = callback;

        final PopMenuView popMenuView = new PopMenuView(this.context, this);
        popMenuView.setAlpha((float) 0.9);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        popMenuView.setLayoutParams(layoutParams);


        final ViewGroup root = (ViewGroup) this.cordova.getActivity().findViewById(android.R.id.content);

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Log.d("UI thread", "I am the UI thread");
                root.addView(popMenuView);
            }
        });
    }


    @Override
    public void onMenuItemSelect(int selectedItem) {
        this.webView.sendJavascript(this.popMenuCallback + "(" + selectedItem + ");");
    }
}
