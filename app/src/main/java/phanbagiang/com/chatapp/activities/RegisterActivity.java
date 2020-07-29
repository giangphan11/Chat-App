package phanbagiang.com.chatapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import phanbagiang.com.chatapp.R;

public class RegisterActivity extends AppCompatActivity {
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        toolbar=findViewById(R.id.reg_toolbar);
        setSupportActionBar(toolbar);
    }
}