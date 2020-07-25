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
import android.widget.TextView;
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
    TextView textView;
    DatabaseReference myRef;
    int size=100000;//for handling async task
    Context context;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=this;
        setContentView(R.layout.activity_main);
        textView=findViewById(R.id.text_feild_counter);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        myRef.child("app_uninstalled").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String value=snapshot.getValue(String.class);
                textView.setText(": "+value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.i("lifecycle_test","resume");
        for(int i=0;i<apps_to_be_removed.size();i++){
            if(!isAppPresent(apps_to_be_removed.get(i).getApp_package())){
                apps_to_be_removed.remove(i);
                int val=Integer.parseInt(textView.getText().toString().substring(2));

                myRef.child("app_uninstalled").setValue(Integer.toString(val+1));

            }
        }


            displayDataInRecycler(apps_to_be_removed,false);

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
                if(apps_list.size()==size){


                    displayDataInRecycler(apps_to_be_removed,true);
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

    private void displayDataInRecycler(List<App> list_to_be_displayed,Boolean checkSize){
        if(list_to_be_displayed.size()==0 && checkSize){
            TextView textView=findViewById(R.id.info_text_feild);
            textView.setText("Your Phone is" +"\n"+"\uD83D\uDC09 Free");
            RecyclerView recyclerView = findViewById(R.id.recycler);
            recyclerView.setVisibility(View.INVISIBLE);
        }

        else {
            RecyclerView recyclerView = findViewById(R.id.recycler);
            //pass app_to_be_removed publishing
            MyGridAdapter adapter = new MyGridAdapter(list_to_be_displayed, context);
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new GridLayoutManager(context, 3));
        }

    }
}