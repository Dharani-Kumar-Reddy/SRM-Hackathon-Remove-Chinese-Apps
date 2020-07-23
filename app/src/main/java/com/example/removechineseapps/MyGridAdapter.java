package com.example.removechineseapps;

import android.content.Context;
import android.content.Intent;
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

import java.util.List;

public class MyGridAdapter extends RecyclerView.Adapter<MyGridAdapter.ViewHolder> {
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
    public void onBindViewHolder(@NonNull MyGridAdapter.ViewHolder holder, int position) {
        final App obj=list.get(position);
        holder.textView.setText(obj.getApp_name());
        //add image resourec

        /*Bitmap bmp = null;
        try {
        URL url = new URL(obj.getApp_logo());
        bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        holder.imageView.setImageBitmap(bmp);*/

        holder.imageView.setImageResource(R.mipmap.ic_launcher);
        //onclick listener
        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DELETE);
                intent.setData(Uri.parse("package:"+obj.getApp_package()));
                context.startActivity(intent);
            }
        });
        Log.i("INside adapter","inside3");

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
