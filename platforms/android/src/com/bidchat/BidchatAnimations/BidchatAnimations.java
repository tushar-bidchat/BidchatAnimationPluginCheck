package com.bidchat.BidchatAnimations;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.BounceInterpolator;
import android.view.animation.CycleInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.view.Display;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;


 import com.bidchat.AnimationPluginCheck.R;
//import com.bidchatapp.bidchat.R;
import com.hanks.htextview.HTextView;
import com.hanks.htextview.HTextViewType;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.Random;

/**
 * This class echoes a string called from JavaScript.
 */
public class BidchatAnimations extends CordovaPlugin implements PopMenuItemClickListener {

    private static final int APP_COLOR = Color.rgb(255, 51, 80);
    private static final int COUNTDOWN_TEXT_SIZE = 40;
    private static final int maxXDispersePoint = 1500;


    private String popMenuCallback;
    private Context context;

    private CountDownTimer mCountDownTimer;
    private HTextView txtCountdown;
    private boolean isCounterOn = false;

    private int minAngle, maxAngle;

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

        if (action.equals("likes")) {

            String x = args.getString(0);
            String y = args.getString(1);

            String userId = args.getString(2);
            String userName = args.getString(3);
            String userImageUrl = args.getString(4);

            showLikes(x, y, userId, userName, userImageUrl);
            return true;
        }

        if (action.equals("lol")) {
            String x = args.getString(0);
            String y = args.getString(1);

            String userId = args.getString(2);
            String userName = args.getString(3);
            String userImageUrl = args.getString(4);

            showLolAnimation(x, y, userId, userName, userImageUrl);
            return true;
        }

        if (action.equals("marryMe")) {

            String x = args.getString(0);
            String y = args.getString(1);

            String userId = args.getString(2);
            String userName = args.getString(3);
            String userImageUrl = args.getString(4);

            showMarryMeAnimation(x, y, userId, userName, userImageUrl);
            return true;
        }

        return false;
    }

    public void stopCountdownTimer(String message) {
        stopCountDown(message);
    }


    public void startCountdownTimer(int timerStartValue, String callback) {

        addSparkleText();
        startCountDown(timerStartValue * 1000, callback);
    }

    private void startCountDown(int startTime, final String callback) {
        if (!isCounterOn) {
            isCounterOn = true;
            mCountDownTimer = new CountDownTimer(startTime + 999, 1000) {
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
                            ((ViewGroup) txtCountdown.getParent()).removeView(txtCountdown);
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
                            ((ViewGroup) txtCountdown.getParent()).removeView(txtCountdown);
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
        txtCountdown.setTextColor(APP_COLOR);
        txtCountdown.setBackgroundColor(Color.TRANSPARENT);
        txtCountdown.setTypeface(null);
        txtCountdown.setAnimateType(HTextViewType.ANVIL);
        txtCountdown.setTextSize(TypedValue.COMPLEX_UNIT_SP, COUNTDOWN_TEXT_SIZE);
        txtCountdown.setTypeface(null, Typeface.BOLD);
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

    public void showLikes(String x, String y , String userId, String userName, String userImageUrl) {

        final ViewGroup rootView = (ViewGroup) this.cordova.getActivity().findViewById(android.R.id.content);
        final RelativeLayout relativeLayout = new RelativeLayout(this.cordova.getActivity());
        DisplayMetrics metrics = this.cordova.getActivity().getResources().getDisplayMetrics();
        int sizeInPx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, metrics);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(sizeInPx, sizeInPx);
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);

        int sizeInPxX = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, Integer.parseInt(x), metrics);
        int sizeInPxY = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, Integer.parseInt(y), metrics);
        
        params.leftMargin =sizeInPxX;
        params.topMargin = sizeInPxY;

        final View view = new View(this.cordova.getActivity());
        view.setLayoutParams(params);

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                rootView.addView(relativeLayout);
                relativeLayout.addView(view);

                view.getViewTreeObserver().addOnGlobalLayoutListener(
                        new ViewTreeObserver.OnGlobalLayoutListener() {
                            @Override
                            public void onGlobalLayout() {
                                // Layout has happened here.
                                createFloatingHeart(cordova.getActivity(), view, relativeLayout);

                                // Don't forget to remove your listener when you are done with it.
                                view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                            }
                        });

            }
        });

    }

    public void createFloatingHeart(Context context, final View view, final ViewGroup parent) {

        final int scaleUpDuration = 150;
        final int scaleDownDuration = 50;

        int animationTime = 0;
        int rotationyCycle = 0;

        DisplayMetrics metrics = this.cordova.getActivity().getResources().getDisplayMetrics();

        final AnimationSet animationSet = new AnimationSet(true);

        // For Pop Up Animation
        Animation animationScaleUp = new ScaleAnimation(0f, 1.2f, 0f, 1.2f, view.getPivotX(), view.getPivotY());
        animationScaleUp.setFillAfter(false); // Needed to keep the result of the animation
        animationScaleUp.setDuration(scaleUpDuration);
        animationScaleUp.setInterpolator(new BounceInterpolator());
        animationSet.addAnimation(animationScaleUp);

        // For Pop Down Animation
        Animation animationScaleDown = new ScaleAnimation(1.2f, 1.0f, 1.2f, 1.0f, view.getPivotX(), view.getPivotY());
        animationScaleDown.setStartOffset(scaleUpDuration);
        animationScaleDown.setFillAfter(false);
        animationScaleDown.setDuration(scaleDownDuration);
        animationScaleDown.setInterpolator(new LinearInterpolator());
        animationSet.addAnimation(animationScaleDown);

         // To generate a random dispersing value between 0 to width of screen
        Random random = new Random();
        Display display = this.cordova.getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        final int NUMBER_OF_CYCLES = 4;
        final int DIP_COVERED_PER_CYCLE = (size.y / 2) / NUMBER_OF_CYCLES;
        final int ANIMATION_TIME = (((size.y / 2)) / DIP_COVERED_PER_CYCLE) * 500;

        int minXDispersePoint = -(int) view.getX();
        int maxXDispersePoint = (int) (size.x - (view.getX() + view.getWidth()));
        int randomXDispersePoint = random.nextInt(maxXDispersePoint - minXDispersePoint) + minXDispersePoint;

        // For Movement Animation
//        int movementInY = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, metrics);
//        Animation animationTranslate = new TranslateAnimation(view.getX(), view.getX() + randomXDispersePoint, view.getY(), movementInY);
        Animation animationTranslate = new TranslateAnimation(view.getX(), view.getX() + randomXDispersePoint, view.getY(), view.getY() - (parent.getHeight() / 2f));// fromXDelta, toXDelta, fromYDelta, toYDelta

        animationTranslate.setFillAfter(true);
        animationTranslate.setDuration(ANIMATION_TIME);
        animationTranslate.setInterpolator(new LinearInterpolator());
        animationSet.addAnimation(animationTranslate);

         // To generate a random angle for each floating heart between -8 to 8
        determineAngle(minXDispersePoint, maxXDispersePoint);

        int randomStartAngle = random.nextInt(maxAngle - minAngle) + minAngle;

        // For Rotate Animation
        Animation animationRotate = new RotateAnimation(0, randomStartAngle, Animation.ABSOLUTE, view.getPivotX(), Animation.ABSOLUTE, view.getPivotY());
        animationRotate.setFillAfter(true);
        animationRotate.setDuration(ANIMATION_TIME / NUMBER_OF_CYCLES);
        animationRotate.setRepeatCount(Animation.INFINITE);
        animationRotate.setRepeatMode(Animation.REVERSE);
        animationRotate.setInterpolator(new CycleInterpolator(Animation.INFINITE));
        animationSet.addAnimation(animationRotate);

        Animation animationAlpha = new AlphaAnimation(1, 0);// fromAlpha, toAlpha
        animationAlpha.setFillAfter(true);
        animationAlpha.setDuration(ANIMATION_TIME / (int) (NUMBER_OF_CYCLES - 0.5));
        animationAlpha.setInterpolator(new LinearInterpolator());
        animationAlpha.setStartOffset((ANIMATION_TIME - (ANIMATION_TIME / NUMBER_OF_CYCLES)));
        animationSet.addAnimation(animationAlpha);

        int widthInPx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, metrics);
        final ImageView imageHeart = new ImageView(context);
        LinearLayout.LayoutParams layoutParamsLeftLetter = new LinearLayout.LayoutParams(widthInPx, ViewGroup.LayoutParams.WRAP_CONTENT);
        imageHeart.setLayoutParams(layoutParamsLeftLetter);
        imageHeart.setAdjustViewBounds(true);
        imageHeart.setImageDrawable(ContextCompat.getDrawable(context.getApplicationContext(), R.drawable.ic_like));
        imageHeart.setAlpha(0.8f);
        parent.addView(imageHeart);
        imageHeart.startAnimation(animationSet);
    }


    /**
     * @param x1 - left end disperse point
     * @param x2 - right disperse point
     */
    public void determineAngle(int x1, int x2) {
        if (x1 == 0 || x1 > (-20)) {
            minAngle = -8;
            maxAngle = -1;
        } else if (x2 == 0 || x2 < (20)) {
            minAngle = 1;
            maxAngle = 8;
        } else if (-(x1) < x2) {
            minAngle = -4;
            maxAngle = 8;
        } else {
            minAngle = -8;
            maxAngle = 4;
        }
    }


    public void showLolAnimation (String x, String y , String userId, String userName, String userImageUrl) {

    }

    public void showMarryMeAnimation (String x, String y, String userId, String userName, String userImageUrl) {
        
    }
}
