package com.example.removechineseapps;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    final List<App> apps_to_be_removed=new ArrayList<>();
    final List<App> apps_list=new ArrayList<>();
    int size=100000;//for handling async task
    Context context;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=this;
        setContentView(R.layout.activity_main);

    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.i("lifecycle_test","resume");
        for(int i=0;i<apps_to_be_removed.size();i++){
            if(!isAppPresent(apps_to_be_removed.get(i).getApp_package())){
                apps_to_be_removed.remove(i);
            }
        }

            RecyclerView recyclerView=findViewById(R.id.recycler);
            //pass app_to_be_removed publishing
            MyGridAdapter adapter=new MyGridAdapter(apps_to_be_removed,context);
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new GridLayoutManager(context,3));
            Log.i("status","list obtained");




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void scan(View v){
        final ProgressDialog progressDoalog = new ProgressDialog(MainActivity.this);
        progressDoalog.setMessage("Its loading....");
        progressDoalog.setTitle("Scanning Your device");
        progressDoalog.show();
        //get data and check for each app in device
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        Log.i("status","starting size");

        myRef.child("length").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                size=Integer.parseInt(snapshot.getValue(String.class));
                Log.i("status","size obtained"+size);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Log.i("status","getting list");





        myRef.child("Apps").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                App app_obj = snapshot.getValue(App.class);
                apps_list.add(app_obj);
                Log.i("Name",Integer.toString(apps_list.size()));
                if(isAppPresent(app_obj.getApp_package())){
                    apps_to_be_removed.add(app_obj);
                    Log.i("status_appsremoved",Integer.toString(apps_list.size()));
                }
                Log.i("status","obj obtained");
                Log.i("status_apps_size",Integer.toString(apps_list.size()));
                if(apps_list.size()==size){


                    RecyclerView recyclerView=findViewById(R.id.recycler);
                    //pass app_to_be_removed publishing
                    MyGridAdapter adapter=new MyGridAdapter(apps_to_be_removed,context);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new GridLayoutManager(context,3));
                    Log.i("status","list obtained");

                    progressDoalog.dismiss();

                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        v.findViewById(R.id.btn_scan).setVisibility(View.INVISIBLE);
    }


    private Boolean isAppPresent(String uri){
            PackageManager pm = getPackageManager();
            try {
                pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
                return true;
            }
            catch (PackageManager.NameNotFoundException e) {
            }

            return false;
    }
}