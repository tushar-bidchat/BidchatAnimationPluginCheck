package com.bidchat.BidchatAnimations;

import android.content.Context;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;

import com.hanks.htextview.HTextView;
import com.hanks.htextview.HTextViewType;

/**
 * This class echoes a string called from JavaScript.
 */
public class BidchatAnimations extends CordovaPlugin implements PopMenuItemClickListener {

    private String popMenuCallback;
    private Context context;

    private CountDownTimer mCountDownTimer;
    private HTextView txtCountdown;
    private boolean isCounterOn = false;

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {

        if (action.equals("showShareMenu")) {
            String callback = args.getString(0);
            this.showShareMenu(context, callback);
            return true;
        }

        if (action.equals("startCountdownTimer")) {
            int timerStartValue = args.getInt(0);
            String callback = args.getString(1);
            this.startCountdownTimer(timerStartValue, callback);
            return true;
        }

        if (action.equals("stopCountdownTimer")) {
            String message = args.getString(0);
            this.stopCountdownTimer(message);
            return true;
        }

        return false;
    }

    public void stopCountdownTimer(String message) {
        stopCountDown(message);
    }


        public void startCountdownTimer(int timerStartValue, String callback) {

        addSparkleText();
        startCountDown(timerStartValue*1000, callback);
    }

    private void startCountDown(int startTime, final String callback) {
        if (!isCounterOn) {
            isCounterOn = true;
            mCountDownTimer = new CountDownTimer(startTime+999, 1000) {
                public void onTick(final long millisUntilFinished) {

                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            txtCountdown.animateText("" + millisUntilFinished / 1000);
                        }
                    });

                    Log.d("Time", "" + millisUntilFinished);
                    if (millisUntilFinished / 1000 == 1) {
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (isCounterOn) {


                                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                                        @Override
                                        public void run() {
                                            txtCountdown.animateText("Time Over");
                                            isCounterOn = false;
                                            BidchatAnimations.this.webView.sendJavascript(callback + "( );");
                                        }
                                    });
                                }
                            }
                        }, 1000);
                    }
                }

                public void onFinish() {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            txtCountdown.animateText("");
                            ((ViewGroup)txtCountdown.getParent()).removeView(txtCountdown);
                        }
                    });
                }
            }.start();
        } else {
            mCountDownTimer.cancel();
            isCounterOn = false;
            startCountDown(startTime, callback);
        }
    }

    private void stopCountDown(final String stopMessage) {
        if (isCounterOn) {
            isCounterOn = false;
            mCountDownTimer.cancel();

            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    txtCountdown.animateText(stopMessage);
                }
            });

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            txtCountdown.animateText("");
                            ((ViewGroup)txtCountdown.getParent()).removeView(txtCountdown);
                        }
                    });


                }
            }, 1000);
        }
    }

    private void addSparkleText() {
        final ViewGroup rootView = (ViewGroup) this.cordova.getActivity().findViewById(android.R.id.content);
        final RelativeLayout relativeLayout = new RelativeLayout(this.cordova.getActivity());
        
        txtCountdown = new HTextView(this.cordova.getActivity());
        txtCountdown.setTextColor(Color.rgb(255, 165, 0));
        txtCountdown.setBackgroundColor(Color.TRANSPARENT);
        txtCountdown.setTypeface(null);
        txtCountdown.setAnimateType(HTextViewType.SPARKLE);
        txtCountdown.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        txtCountdown.setLayoutParams(layoutParams);
        txtCountdown.setGravity(Gravity.CENTER);

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                rootView.addView(relativeLayout);
                relativeLayout.addView(txtCountdown);
            }
        });
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
