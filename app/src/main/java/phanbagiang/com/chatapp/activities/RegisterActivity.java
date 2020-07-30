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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import phanbagiang.com.chatapp.R;

public class RegisterActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextInputEditText userName, eMail, passWord;
    MaterialButton btnRegister;
    ProgressBar progressBar;
    // Firebase
    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        toolbar=findViewById(R.id.reg_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        addControls();
        addEvents();
    }
    private void addEvents(){
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnRegister.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                String name=userName.getText().toString();
                String pass=passWord.getText().toString();
                String email=eMail.getText().toString();
                if(name.isEmpty()||pass.isEmpty()||email.isEmpty()){
                    showMessage("Ô nhập liệu không được để trống!");
                    btnRegister.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
                }
                else{
                    register(name,email,pass);
                }

            }
        });
    }
    private void showMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    private void register(final String userName, final String mail, String pass){
        firebaseAuth.createUserWithEmailAndPassword(mail,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    showMessage("createUserWithEmail:success");
                    FirebaseUser user=firebaseAuth.getCurrentUser();

                    String id=user.getUid();
                    HashMap<String,String>hashMap=new HashMap<>();
                    hashMap.put("id",id);
                    hashMap.put("name",userName);
                    hashMap.put("mail",mail);
                    hashMap.put("image","default");

                    DatabaseReference reference= FirebaseDatabase.getInstance().
                            getReference("users");
                            reference.child(id).setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Intent intent=new Intent(RegisterActivity.this,MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            }
                            else{
                                showMessage(task.getException().getMessage());
                                btnRegister.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.INVISIBLE);
                            }
                        }
                    });
                }
                else{
                    showMessage(task.getException().getMessage());
                    btnRegister.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }
        });
    }
    private void addControls(){
        progressBar=findViewById(R.id.progressBar2);
        btnRegister=findViewById(R.id.btnRegister);
        userName=findViewById(R.id.edtName);
        eMail=findViewById(R.id.edtEmail);
        passWord=findViewById(R.id.edtPassWord);
        firebaseAuth=FirebaseAuth.getInstance();

        btnRegister.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
    }

}