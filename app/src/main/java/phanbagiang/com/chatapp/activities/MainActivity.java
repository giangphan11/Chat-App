package phanbagiang.com.chatapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import de.hdodenhof.circleimageview.CircleImageView;
import phanbagiang.com.chatapp.R;

public class MainActivity extends AppCompatActivity {
    CircleImageView imgUser;
    TextView txtUsername;

    Toolbar toolbar;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    DatabaseReference mReference;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Main");

        addControls();

    }
    private void addControls(){
        firebaseAuth=FirebaseAuth.getInstance();
        user= firebaseAuth.getCurrentUser();
        imgUser=findViewById(R.id.imgUser);
        txtUsername=findViewById(R.id.txtUserName);
        txtUsername.setText(user.getDisplayName());
        mReference=FirebaseDatabase.getInstance().getReference();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=this.getMenuInflater();
        menuInflater.inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.mnuLogout:
                FirebaseAuth.getInstance().signOut();
                Intent intent=new Intent(MainActivity.this,StartActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}