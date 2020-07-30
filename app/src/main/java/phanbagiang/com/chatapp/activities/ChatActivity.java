package phanbagiang.com.chatapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import phanbagiang.com.chatapp.R;
import phanbagiang.com.chatapp.model.User;

public class ChatActivity extends AppCompatActivity {
    User userSelected=null;
    Toolbar toolbar;
    CircleImageView circleImageView;
    TextView txtName;
    EditText chat_sms;
    ImageView btnSend;

    //Firebase
    FirebaseUser user;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        addControls();
        addEvents();
    }
    private void addEvents(){
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sms=chat_sms.getText().toString();
                String receiverId=userSelected.getId();
                String senderId=user.getUid();
                if(sms.isEmpty()){
                    Toast.makeText(ChatActivity.this, "Sending false!", Toast.LENGTH_SHORT).show();
                }
                else{
                    sendMessage(sms,senderId,receiverId);
                }
                chat_sms.setText("");
            }
        });
    }
    private void sendMessage(String sms, String sender, String receiver){
        HashMap<String,Object> hashMap=new HashMap<>();
        hashMap.put("message",sms);
        hashMap.put("sender",sender);
        hashMap.put("receiver",receiver);
        reference.child("chats").push().setValue(hashMap);
    }
    private void addControls(){
        Intent intent=getIntent();
        circleImageView=findViewById(R.id.imgUser);
        txtName=findViewById(R.id.txtUserName);
        userSelected= (User) intent.getSerializableExtra("user");
        txtName.setText(userSelected.getName());
        if(userSelected.getImage().equals("default")){
            circleImageView.setImageResource(R.mipmap.ic_launcher);
        }
        else{
            Glide.with(ChatActivity.this)
                    .load(userSelected.getImage())
                    .into(circleImageView);
        }

        chat_sms=findViewById(R.id.chat_sms);
        btnSend=findViewById(R.id.btnSend);
        user= FirebaseAuth.getInstance().getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference();
    }
}