package com.bidchat.BidchatAnimations;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class PopMenuActivity extends AppCompatActivity {

    private List<MenuItem> menuItems = new ArrayList<MenuItem>();
    private MenuAdapter simpleMenuAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FakeR fakeR = new FakeR(PopMenuActivity.this);
        
        setContentView(fakeR.getId("layout", "layout_pop_menu"));
        
        menuItems.add(new MenuItem("Facebook", fakeR.getId("drawable", "facebook")));
        menuItems.add(new MenuItem("Twitter", fakeR.getId("drawable", "twitter")));
        menuItems.add(new MenuItem("Instagram", fakeR.getId("drawable", "instagram")));
        menuItems.add(new MenuItem("Google+", fakeR.getId("drawable", "google_plus")));
        menuItems.add(new MenuItem("Email", fakeR.getId("drawable", "email")));
        menuItems.add(new MenuItem("Message", fakeR.getId("drawable", "messages")));

        simpleMenuAdapter = new MenuAdapter();

        RecyclerView recyclerView = (RecyclerView) findViewById(fakeR.getId("id", "main_recycler_view"));

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setItemAnimator(new MenuAnimator());
        recyclerView.getItemAnimator().setAddDuration(300);
        recyclerView.getItemAnimator().setRemoveDuration(300);
        recyclerView.setAdapter(simpleMenuAdapter);

        recyclerView.addOnItemTouchListener(new MenuTouchListener(PopMenuActivity.this, new MenuClickListener() {
            @Override
            public void onClick(View view, int position) {
                Log.d("Check", String.valueOf(position));

                hideMenu();

            }
        }));

        Button buttonCancel = (Button) findViewById(fakeR.getId("id", "main_button_cancel"));
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideMenu();
            }
        });

        showMenu();
    }

    private void hideMenu() {

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
                            finish();
                        }
                    }, 500);
                }

                hideMenu();
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
