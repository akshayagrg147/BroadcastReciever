package com.meetsuccess.broadcastbattrychanger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity  {
BattryReciever battryReciever;
Boolean registered=false;
TextView txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt=findViewById(R.id.text);
        battryReciever=new BattryReciever(txt);
        findViewById(R.id.Start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerReceiver(battryReciever,new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
                findViewById(R.id.stopService).setVisibility(View.VISIBLE);
                registered=true;
            }
        });
        findViewById(R.id.stopService).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(registered) {
                    battryReciever.stopMediaplayer();
                    unregisterReceiver(battryReciever);
                    registered=false;
                }
            }
        });

    }



    @Override
    protected void onStop() {
        super.onStop();

        if(registered) {
            battryReciever.stopMediaplayer();
            unregisterReceiver(battryReciever);
            PeriodicWorkRequest mPeriodicWorkRequest;
            mPeriodicWorkRequest = new PeriodicWorkRequest.Builder(MyPeriodicWork.class,
                    15, TimeUnit.MINUTES)
                    .addTag("periodicWorkRequest")
                    .build();

            WorkManager.getInstance().enqueue(mPeriodicWorkRequest);
            registered=false;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(battryReciever);
    }
    public class MyPeriodicWork extends Worker {


        public MyPeriodicWork(@NonNull Context context, @NonNull WorkerParameters workerParams) {
            super(context, workerParams);
        }


        @NonNull
        @Override
        public Result doWork() {
            Log.d("Calling","calling");

//            registerReceiver(battryReciever,new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
//            findViewById(R.id.stopService).setVisibility(View.VISIBLE);

            return Result.success();
        }
    }

}