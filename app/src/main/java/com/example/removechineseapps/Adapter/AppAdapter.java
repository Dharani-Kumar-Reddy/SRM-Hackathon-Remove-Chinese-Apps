package com.example.removechineseapps.Adapter;

import androidx.recyclerview.widget.RecyclerView;

import com.example.removechineseapps.App;
import com.example.removechineseapps.Interface.ItemClickListner;
import com.example.removechineseapps.R;
import com.example.removechineseapps.ViewHolder.AppViewHolder;
import com.squareup.picasso.Picasso;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.List;


public class AppAdapter extends RecyclerView.Adapter<AppAdapter.ViewHolder> {
    private List<App> applist;

    // RecyclerView recyclerView;
    public AppAdapter(List<App> applist) {
        this.applist = applist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final App listdata = applist.get(position);
        holder.txtAppName.setText(listdata.getApp_name());
        holder.txtAppPackage.setText(listdata.getApp_package());

        Picasso.get().load(listdata.getApp_logo()).into(holder.appLogo);

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),"click on item: "+listdata.getApp_name(),Toast.LENGTH_LONG).show();
            }
        });
    }



    @Override
    public int getItemCount() {
        return applist.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtAppName,txtAppPackage;
        public ImageView appLogo;
        public ItemClickListner Listner;
        public RelativeLayout relativeLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            appLogo=(ImageView)itemView.findViewById(R.id.app_logo);
            txtAppName=(TextView)itemView.findViewById(R.id.app_name);
            txtAppPackage=(TextView)itemView.findViewById(R.id.app_package);

        }
    }
}