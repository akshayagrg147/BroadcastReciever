package com.meetsuccess.broadcastbattrychanger;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.widget.TextView;

public class BattryReciever extends BroadcastReceiver {
    TextView textView;
    MediaPlayer sound;
    BattryReciever(TextView vv)
    {
this.textView=vv;
    }
    void stopMediaplayer()
    {
        if(sound!=null)
        {
            sound.stop();
        }

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        int percentage=intent.getIntExtra("level",0);

        textView.setText(percentage+"%");
        if(percentage==100){
            sound = MediaPlayer.create(context, R.raw.rintone);
            sound.start();
        }


    }
}
