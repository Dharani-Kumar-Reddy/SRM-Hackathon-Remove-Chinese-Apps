package com.example.removechineseapps.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.removechineseapps.App;
import com.example.removechineseapps.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class appAdapter1 extends RecyclerView.Adapter<appAdapter1.appViewHolder> {
private List<App> mAppList;
    public static class appViewHolder extends RecyclerView.ViewHolder
{   public TextView txtAppName,txtAppPackage;
    public ImageView appLogo;

    public appViewHolder(@NonNull View itemView) {
        super(itemView);
        appLogo=(ImageView)itemView.findViewById(R.id.app_logo);
        txtAppName=(TextView)itemView.findViewById(R.id.app_name);
        txtAppPackage=(TextView)itemView.findViewById(R.id.app_package);

    }
}
public appAdapter1(List<App> appList){
        mAppList=appList;

}

    @NonNull
    @Override
    public appViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.app_layout,parent,false);
        appViewHolder avh=new appViewHolder(v);
        return avh;
}

    @Override
    public void onBindViewHolder(@NonNull appViewHolder holder, int position) {
        App currentApp=mAppList.get(position);
        holder.txtAppName.setText(currentApp.getApp_name());
        holder.txtAppPackage.setText(currentApp.getApp_package());
        Picasso.get().load(currentApp.getApp_logo()).into(holder.appLogo);
    }

    @Override
    public int getItemCount() {
        return mAppList.size();
    }
}
