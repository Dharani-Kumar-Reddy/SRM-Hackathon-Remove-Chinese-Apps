package com.example.removechineseapps;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInstaller;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;


public class MyGridAdapter extends RecyclerView.Adapter<MyGridAdapter.ViewHolder> {
    static final int REQUEST_INSTALL = 1;
    static final int REQUEST_UNINSTALL = 2;
    private List<App> list;
    private Context context;
    public MyGridAdapter(List<App> list, Context context){
        this.context=context;
        this.list=list;
        Log.i("INside adapter","inside1");
    }
    @NonNull
    @Override
    public MyGridAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.app_view,parent,false);
        ViewHolder viewHolder=new ViewHolder(v);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final App obj=list.get(position);
        holder.textView.setText(obj.getApp_name());
        //add image resourec

        Picasso.get().load(obj.getApp_logo()).placeholder(R.drawable.loading).into(holder.imageView);

        //onclick listener
        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DELETE);
                intent.setData(Uri.parse("package:"+obj.getApp_package()));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

                Log.i("lifecycle_test","CHECK FROM HERE");
                //code to remove from recycler view
                /*int newPosition = holder.getAdapterPosition();
                list.remove(newPosition);
                notifyItemRemoved(newPosition);
                notifyItemRangeChanged(newPosition, list.size());*/

            }
        });




    }


    @Override
    public int getItemCount() {
        Log.i("INside adapter",Integer.toString(list.size()));
        return list.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView imageView;
        public TextView textView;
        public Button btn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.btn=itemView.findViewById(R.id.btn_uninstall);
            this.imageView=itemView.findViewById(R.id.app_icon_field);
            this.textView=itemView.findViewById(R.id.app_name_fiels);
            Log.i("INside adapter","inside4");
        }
    }
}
