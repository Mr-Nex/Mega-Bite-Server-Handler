package com.example.rexx.megabiteserver.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rexx.megabiteserver.Common.Common;
import com.example.rexx.megabiteserver.Interface.ItemClickListener;
import com.example.rexx.megabiteserver.R;

public class MenuViewHolder extends RecyclerView.ViewHolder implements
        View.OnClickListener, View.OnCreateContextMenuListener {

    public TextView menu_name;
    public ImageView menu_image;

    private ItemClickListener itemClickListener;

    public MenuViewHolder(View itemView) {
        super(itemView);

        menu_name = (TextView)itemView.findViewById(R.id.menu_name);
        menu_image = (ImageView)itemView.findViewById(R.id.menu_image);

        itemView.setOnCreateContextMenuListener(this);
        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {

        itemClickListener.onClick(view, getAdapterPosition(), false);

    }

    @Override
    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
        contextMenu.setHeaderTitle("Select Your Action");

        contextMenu.add(0,0,getAdapterPosition(), Common.UPDATE);
        contextMenu.add(0,1,getAdapterPosition(), Common.DELETE);
    }
}
