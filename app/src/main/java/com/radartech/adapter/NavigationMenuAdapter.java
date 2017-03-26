package com.radartech.adapter;

import android.content.res.TypedArray;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.radartech.callbacks.OnItemClickListener;
import com.radartech.sw.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created on 31 Oct 2016 4:54 PM.
 */

public class NavigationMenuAdapter extends RecyclerView.Adapter<NavigationMenuAdapter.MenuViewHolder> {

    private String[] mMenuData;
    private TypedArray mMenuIconData;
    private OnItemClickListener mOnMenuItemClickListener;

    public void setMenuData(String[] menuData, TypedArray menuIconData) {
        this.mMenuIconData = menuIconData;
        this.mMenuData = menuData;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnMenuItemClickListener = onItemClickListener;
    }

    @Override
    public MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.navigation_menu_item, parent, false);
        return new MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MenuViewHolder holder, int position) {
            holder.mMenuIV.setImageResource(mMenuIconData.getResourceId(position, -1));
            holder.mMenuTV.setText(mMenuData[position]);
        if (position == mMenuData.length) {
            holder.mSeparator.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return mMenuData.length;
    }

    class MenuViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.menu_imageView)
        ImageView mMenuIV;
        @BindView(R.id.menu_textView)
        TextView mMenuTV;
        @BindView(R.id.menu_separatorView)
        View mSeparator;

        public MenuViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.menu_parent_ll)
        public void onItemClick(View view) {
            mOnMenuItemClickListener.onItemClick(view, getAdapterPosition());
        }
    }
}
