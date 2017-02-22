package com.bidchat.BidchatAnimations;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tushar
 * Created on 12/26/16.
 */

class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {

    private List<MenuItem> menuItems = new ArrayList<MenuItem>();
    private FakeR fakeR;

    MenuAdapter() {

    }

    @Override
    public MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        fakeR = new FakeR(parent.getContext());
        return new MenuViewHolder(LayoutInflater.from(parent.getContext()).inflate(fakeR.getId("layout", "menu_item"), parent, false));
    }

    @Override
    public void onBindViewHolder(MenuViewHolder holder, int position) {
        holder.imageView.setImageResource(menuItems.get(position).getImageId());
        holder.textView.setText(menuItems.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return this.menuItems.size();
    }

    void addMenuItem(MenuItem menuItem) {
        menuItems.add(menuItem);
    }

    void removeMenuItem(int index) {
        menuItems.remove(index);
    }

    int menuItemCount() {
        return menuItems.size();
    }

    class MenuViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;

        MenuViewHolder(View itemView) {
            super(itemView);

            textView = (TextView) itemView.findViewById(fakeR.getId("id", "menu_item_name"));
            imageView = (ImageView) itemView.findViewById(fakeR.getId("id", "menu_item_image"));
        }
    }
}