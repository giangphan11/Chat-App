package phanbagiang.com.chatapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import phanbagiang.com.chatapp.R;

public class MainActivity extends AppCompatActivity {
    FirebaseDatabase mDatabase;
    DatabaseReference mReference;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDatabase=FirebaseDatabase.getInstance();
        mReference=mDatabase.getReference();

    }

}