package com.bidchat.BidchatAnimations;

import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.animation.Interpolator;

import jp.wasabeef.recyclerview.animators.BaseItemAnimator;

/**
 * Created by Tushar
 * Created on 12/26/16.
 */

class MenuAnimator extends BaseItemAnimator {

    private class JellyBounceInterpolator implements Interpolator {

        @Override
        public float getInterpolation(float ratio) {
            if (ratio == 0.0f || ratio == 1.0f)
                return ratio;
            else {
                float p = 0.6f;
                float two_pi = (float) (Math.PI * 2.7f);
                return (float) Math.pow(2.0f, -10.0f * ratio) * (float) Math.sin((ratio - (p / 5.0f)) * two_pi / p) + 1.0f;
            }
        }
    }

    MenuAnimator() {
        mInterpolator = new JellyBounceInterpolator();
    }

    @Override
    protected void animateRemoveImpl(RecyclerView.ViewHolder holder) {

        ViewCompat.animate(holder.itemView)
                .translationY(500)
                .alpha(0)
                .setDuration(2000)
                .setInterpolator(mInterpolator)
                .setListener(new DefaultRemoveVpaListener(holder))
                .setStartDelay(200)
                .start();
    }

    @Override
    protected void preAnimateAddImpl(RecyclerView.ViewHolder holder) {
        ViewCompat.setTranslationY(holder.itemView, holder.itemView.getRootView().getTop() + holder.itemView.getRootView().getHeight()); // recyclerView.getTop() + recyclerView.getHeight());
        ViewCompat.setAlpha(holder.itemView, 0);
    }

    @Override
    protected void preAnimateRemoveImpl(RecyclerView.ViewHolder holder) {
        super.preAnimateRemoveImpl(holder);
        ViewCompat.setTranslationY(holder.itemView, holder.itemView.getRootView().getTop()); // recyclerView.getTop() + recyclerView.getHeight());
        ViewCompat.setAlpha(holder.itemView, 1);
    }

    @Override
    protected void animateAddImpl(RecyclerView.ViewHolder holder) {

        ViewCompat.animate(holder.itemView)
                .translationY(0)
                .alpha(1)
                .setDuration(2000)
                .setInterpolator(mInterpolator)
                .setListener(new DefaultAddVpaListener(holder))
                .setStartDelay(200)
                .start();

    }
}
