package com.bidchat.BidchatAnimations;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

public class PopMenuView extends RelativeLayout {

    private List<MenuItem> menuItems = new ArrayList<MenuItem>();
    private MenuAdapter simpleMenuAdapter;
    private PopMenuItemClickListener listener;

    public PopMenuView(Context context, PopMenuItemClickListener popMeuListener) {
        super(context);
        listener = popMeuListener;
        init(context, null, 0);
    }

    public PopMenuView(Context context, AttributeSet attrs, PopMenuItemClickListener popMeuListener) {
        super(context, attrs);
        listener = popMeuListener;
        init(context, attrs, 0);
    }

    public PopMenuView(Context context, AttributeSet attrs, int defStyle, PopMenuItemClickListener popMeuListener) {
        super(context, attrs, defStyle);
        listener = popMeuListener;
        init(context, attrs, defStyle);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {

        FakeR fakeR = new FakeR(context);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(fakeR.getId("layout", "layout_pop_menu"), this, true);

        menuItems.add(new MenuItem("Facebook", fakeR.getId("drawable", "facebook")));
        menuItems.add(new MenuItem("Twitter", fakeR.getId("drawable", "twitter")));
        menuItems.add(new MenuItem("Instagram", fakeR.getId("drawable", "instagram")));
        menuItems.add(new MenuItem("Google+", fakeR.getId("drawable", "google_plus")));
        menuItems.add(new MenuItem("Email", fakeR.getId("drawable", "email")));
        menuItems.add(new MenuItem("Message", fakeR.getId("drawable", "messages")));

        simpleMenuAdapter = new MenuAdapter();

        RecyclerView recyclerView = (RecyclerView) view.findViewById(fakeR.getId("id", "main_recycler_view"));
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 2));
        recyclerView.setItemAnimator(new MenuAnimator());
        recyclerView.getItemAnimator().setAddDuration(300);
        recyclerView.getItemAnimator().setRemoveDuration(300);
        recyclerView.setAdapter(simpleMenuAdapter);

        recyclerView.addOnItemTouchListener(new MenuTouchListener(context, new MenuClickListener() {
            @Override
            public void onClick(View view, int position) {
                Log.d("Check", String.valueOf(position));

                hideMenu(position);

            }
        }));

        Button buttonCancel = (Button) view.findViewById(fakeR.getId("id", "main_button_cancel"));
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideMenu(-1);
            }
        });

        showMenu();
    }


    private void hideMenu(final int selectedItem) {

        if (simpleMenuAdapter.menuItemCount() == 0) {
            return;
        }

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                simpleMenuAdapter.removeMenuItem(simpleMenuAdapter.menuItemCount() - 1);
                simpleMenuAdapter.notifyItemRemoved(simpleMenuAdapter.menuItemCount());

                if (simpleMenuAdapter.menuItemCount() == 0) {

                    Handler handler1 = new Handler();
                    handler1.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            ((ViewGroup) PopMenuView.this.getParent()).removeView(PopMenuView.this);

                            if (selectedItem > -1) {
                                listener.onMenuItemSelect(selectedItem);
                            }
                        }
                    }, 500);
                }

                hideMenu(selectedItem);
            }
        }, 0);

    }

    public void showMenu() {

        for (final MenuItem menuItem : menuItems) {

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    simpleMenuAdapter.addMenuItem(menuItem);
                    simpleMenuAdapter.notifyItemInserted(simpleMenuAdapter.getItemCount() + 1);
                }
            }, 400);
        }
    }
}
