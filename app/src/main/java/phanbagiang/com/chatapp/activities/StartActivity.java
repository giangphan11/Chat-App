package phanbagiang.com.chatapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import phanbagiang.com.chatapp.R;

public class StartActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        firebaseAuth=FirebaseAuth.getInstance();
    }

    public void loginEvent(View view) {
        Intent intent=new Intent(StartActivity.this,LoginActivity.class);
        startActivity(intent);
    }

    public void registerEvent(View view) {
        Intent intent=new Intent(StartActivity.this,RegisterActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user=firebaseAuth.getCurrentUser();
        if(user!=null){
            Intent intent=new Intent(StartActivity.this,MainActivity.class);
            //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }
    }
}