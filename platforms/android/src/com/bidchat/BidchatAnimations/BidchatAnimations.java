package com.bidchat.BidchatAnimations;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import org.apache.cordova.CordovaActivity;
import org.apache.cordova.CordovaWebView;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.lang.reflect.Field;

/**
 * This class echoes a string called from JavaScript.
 */
public class BidchatAnimations extends CordovaPlugin implements PopMenuItemClickListener {

    private static Field appViewField;
    private String popMenuCallback;
    private Context context;

    /* Cordova webview instance
    */
    static {
        try {
            Class<?> cdvActivityClass = CordovaActivity.class;
            Field wvField = cdvActivityClass.getDeclaredField("appView");
            wvField.setAccessible(true);
            appViewField = wvField;
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("showYesNoAlert")) {
            String message = args.getString(0);
            this.showYesNoAlert(message, callbackContext);
            return true;
        }

        if (action.equals("showShareMenu")) {
            String callback = args.getString(0);
            this.showShareMenu(context, callback);
            return true;
        }
        return false;
    }

    private void showYesNoAlert(String message, CallbackContext callbackContext) {
        if (message != null && message.length() > 0) {
            callbackContext.success(message);
        } else {
            callbackContext.error("Expected one non-empty string argument.");
        }
    }

     public void showShareMenu(Context context, String callback) {

        this.context = context;
        this.popMenuCallback = callback;

        PopMenuView popMenuView = new PopMenuView(this.context, this);
        popMenuView.setAlpha((float) 0.9);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        popMenuView.setLayoutParams(layoutParams);

        // Get the Parent View
        View view;
        try {
            CordovaWebView cordovaWebView = (CordovaWebView) appViewField.get(context);
            view = cordovaWebView.getView();
            ((ViewGroup) view.getParent()).addView(popMenuView);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


     @Override
    public void onMenuItemSelect(int selectedItem) {

        try {
            final CordovaWebView webView = (CordovaWebView) appViewField.get(this.context);
            webView.sendJavascript(this.popMenuCallback + "(" + selectedItem + ");");
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
