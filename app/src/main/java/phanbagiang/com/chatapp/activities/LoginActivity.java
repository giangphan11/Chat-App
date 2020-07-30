package phanbagiang.com.chatapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import phanbagiang.com.chatapp.R;

public class LoginActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextInputEditText mail,pass;
    MaterialButton btnLogin;
    ProgressBar progressBar;

    //Firebae
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Login");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        addControls();
        addEvents();

    }
    private void addEvents(){
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnLogin.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                String m=mail.getText().toString();
                String pa=pass.getText().toString();
                if(m.isEmpty()||pa.isEmpty()){
                    showMessage("Các trường không được để trống!");
                    btnLogin.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);

                }
                else{
                    logIn(m,pa);
                }
            }
        });
    }
    private void logIn(String mail, String pass){
        firebaseAuth.signInWithEmailAndPassword(mail,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    showMessage("signInWithEmail:success");
                    Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }
                else{
                    showMessage(task.getException().getMessage());
                    btnLogin.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }
        });
    }
    private void showMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
    private void addControls(){
        mail=findViewById(R.id.log_mail);
        btnLogin=findViewById(R.id.log_btnLogin);
        pass=findViewById(R.id.log_password);
        progressBar=findViewById(R.id.progressBar);

        btnLogin.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
        firebaseAuth=FirebaseAuth.getInstance();
    }
}