package com.example.removechineseapps.ViewHolder;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.removechineseapps.Interface.ItemClickListner;
import com.example.removechineseapps.R;

public class AppViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public TextView txtAppName,txtAppPackage;
    public ImageView appLogo;
    public ItemClickListner Listner;

    public AppViewHolder(@NonNull View itemView) {
        super(itemView);

        appLogo=(ImageView)itemView.findViewById(R.id.app_logo);
        txtAppName=(TextView)itemView.findViewById(R.id.app_name);
        txtAppPackage=(TextView)itemView.findViewById(R.id.app_package);



    }

    public void setItemClickListner(ItemClickListner listner)
    {
        this.Listner=listner;

    }


    @Override
    public void onClick(View view) {
        Listner.onClick(view,getAdapterPosition(),false);
    }
}