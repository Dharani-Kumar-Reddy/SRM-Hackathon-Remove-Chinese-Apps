package com.example.removechineseapps;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.removechineseapps.Adapter.appAdapter1;
import com.example.removechineseapps.App;
import com.example.removechineseapps.ViewHolder.AppViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static final String TAG = "MainActivity";

    private DatabaseReference AppRef;
    private List<App> appList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppRef= FirebaseDatabase.getInstance().getReference().child("Apps");
        appList=new ArrayList<>();
        getPackages();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

            }
        }, 20000);
        check(appList);
        Log.i("Showing" , Integer.toString(appList.size()));
        mRecyclerView=findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager=new LinearLayoutManager(this);
        mAdapter=new appAdapter1(appList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);


    }

    private void check(List<App> appList) {

        Log.i("Showing", Integer.toString(appList.size()));
        for(int pos=0;pos<appList.size();pos++) {
            App obj = appList.get(pos);
            Log.i("Showing" + obj.getApp_name(), Integer.toString(appList.size()));

        }
    }



   /* private void add_listner(List<App> appList) {

        FirebaseRecyclerOptions<App> options=
                new FirebaseRecyclerOptions.Builder<App>()
                        .setQuery(AppRef,App.class)
                        .build();

        FirebaseRecyclerAdapter<App, AppViewHolder> adapter=
                new FirebaseRecyclerAdapter<App, AppViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull AppViewHolder holder, int position, @NonNull App model) {
                        holder.txtAppName.setText(model.getApp_name());
                        holder.txtAppPackage.setText(model.getApp_package());

                        Picasso.get().load(model.getApp_logo()).into(holder.appLogo);

                    }

                    @NonNull
                    @Override
                    public AppViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.app_layout,parent,false);
                        AppViewHolder holder=new AppViewHolder(view);
                        return holder;
                    };
                };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    } */

    private  void getPackages(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("Apps");

        List<App> apps_to_be_removed=new ArrayList<>();
        final List<App> apps_list=new ArrayList<>();

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                App app_obj = snapshot.getValue(App.class);

                Log.i("Name"+ app_obj.getApp_name(),Integer.toString(appList.size()));
                if(isAppPresent(app_obj.getApp_package())){
                    appList.add(app_obj);
                    Log.i("Remove"+app_obj.getApp_name(),Integer.toString(appList.size()));
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
       /* for(int pos=0;pos<apps_list.size();pos++){
            App obj=apps_list.get(pos);
            Log.i("Name"+ obj.getApp_name(),Integer.toString(apps_list.size()));
            if(isAppPresent(obj.getApp_package())){
                apps_to_be_removed.add(obj);
                Log.i("Remove"+ obj.getApp_name(),Integer.toString(apps_list.size()));
            }
        }*/

    }

    private Boolean isAppPresent(String uri){
            PackageManager pm = getPackageManager();
            try {
                pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
                return true;
            } catch (PackageManager.NameNotFoundException e) {
            }

            return false;
    }
}