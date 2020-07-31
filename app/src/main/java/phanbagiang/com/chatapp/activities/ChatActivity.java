package phanbagiang.com.chatapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import phanbagiang.com.chatapp.R;
import phanbagiang.com.chatapp.adapter.ChatAdapter;
import phanbagiang.com.chatapp.model.Chat;
import phanbagiang.com.chatapp.model.User;

public class ChatActivity extends AppCompatActivity {
    String receiverId=null;
    Toolbar toolbar;
    CircleImageView circleImageView;
    TextView txtName;
    EditText chat_sms;
    ImageView btnSend;

    //Firebase
    FirebaseUser userGui;
    DatabaseReference reference;

    //list_chat_sms
    RecyclerView listChat;
    ChatAdapter chatAdapter;
    List<Chat>mData;
    private static final String TAG = "ChatActivity";
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
                String senderId=userGui.getUid();
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
        DatabaseReference reference2=FirebaseDatabase.getInstance().getReference();
        HashMap<String,Object> hashMap=new HashMap<>();
        hashMap.put("message",sms);
        hashMap.put("sender",sender);
        hashMap.put("receiver",receiver);
        reference2.child("chats").push().setValue(hashMap);
    }
    private void readSMS(final String ngNhan, final String ngGui,final String imgUrl){
        mData=new ArrayList<>();
        //

        //
        reference=FirebaseDatabase.getInstance().getReference("chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mData.clear();
                for(DataSnapshot data:dataSnapshot.getChildren()){
                    Chat chat=data.getValue(Chat.class);
                    if(chat.getSender().equals(ngGui)&&chat.getReceiver().equals(ngNhan)||
                        chat.getSender().equals(ngNhan)&&chat.getReceiver().equals(ngGui)){
                        mData.add(chat);
                        //Log.e(TAG, "onDataChange: "+chat.getMessage() );
                    }
                }
                chatAdapter=new ChatAdapter(getApplicationContext(),mData,imgUrl);
                listChat.setAdapter(chatAdapter);
                //
                listChat.setHasFixedSize(true);
                LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getApplicationContext());
                linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
                linearLayoutManager.setStackFromEnd(true);
                listChat.setLayoutManager(linearLayoutManager);
                //chatAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void addControls(){
        listChat=findViewById(R.id.list_message);
        /*
        listChat.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        linearLayoutManager.setStackFromEnd(true);
        listChat.setLayoutManager(linearLayoutManager);

         */

        Intent intent=getIntent();
        circleImageView=findViewById(R.id.imgUser);
        txtName=findViewById(R.id.txtUserName);
        receiverId=intent.getStringExtra("userId");

        chat_sms=findViewById(R.id.chat_sms);
        btnSend=findViewById(R.id.btnSend);
        userGui= FirebaseAuth.getInstance().getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference("users");

        reference.child(receiverId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user=dataSnapshot.getValue(User.class);
                txtName.setText(user.getName());
                if(user.getImage().equals("default")){
                    circleImageView.setImageResource(R.mipmap.ic_launcher);
                }
                else{
                    Glide.with(ChatActivity.this).load(user.getImage()).into(circleImageView);
                }
                readSMS(user.getId(),FirebaseAuth.getInstance().getUid(),user.getImage());
                //
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ChatActivity.this, ""+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }
}