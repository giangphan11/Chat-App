package phanbagiang.com.chatapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;

import java.util.Timer;
import java.util.TimerTask;

import phanbagiang.com.chatapp.R;

public class WellcomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wellcome);

        Handler handler=new Handler();
        handler.postDelayed(new TimerTask() {
            @Override
            public void run() {
                Intent intent=new Intent(WellcomeActivity.this,StartActivity.class);
                startActivity(intent);
                finish();
            }
        },1000);

        /*
        Timer timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent=new Intent(WellcomeActivity.this,StartActivity.class);
                startActivity(intent);
                finish();
            }
        },1000);

         */
    }
}