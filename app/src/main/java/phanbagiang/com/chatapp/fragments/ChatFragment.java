package phanbagiang.com.chatapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import phanbagiang.com.chatapp.R;
import phanbagiang.com.chatapp.activities.ChatActivity;
import phanbagiang.com.chatapp.adapter.IEventsRecycler;
import phanbagiang.com.chatapp.adapter.UserAdapter;
import phanbagiang.com.chatapp.model.Chat;
import phanbagiang.com.chatapp.model.User;

public class ChatFragment extends Fragment implements IEventsRecycler {
    RecyclerView recyclerView;
    UserAdapter adapter;
    //List<String>userList;

    HashSet<String>userList;
    List<User>mData;

    FirebaseUser mUser;
    DatabaseReference mReference;
    private static final String TAG = "ChatFragment";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_chat,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addControls(view);
        addEvents();
    }

    private void addEvents() {

        mReference.child("chats").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userList.clear();
                for(DataSnapshot data:dataSnapshot.getChildren()){
                    Chat chat=data.getValue(Chat.class);
                    if(chat.getSender().equals(mUser.getUid())){
                        userList.add(chat.getReceiver());
                    }
                    if(chat.getReceiver().equals(mUser.getUid())){
                        userList.add(chat.getSender());
                    }
                }
                //Log.e(TAG, "onDataChange: "+userList.toString() );
                readChats();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void readChats(){
        mReference.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mData.clear();
                for(DataSnapshot data:dataSnapshot.getChildren()){
                    User user=data.getValue(User.class);
                    for(String id:userList){
                        if(user.getId().equals(id)){
                            mData.add(user);
                            /*
                            if(mData.size()!=0){ // if mData is not empty

                                for(User user1 :mData){
                                    if(!user.getId().equals(user1.getId())){
                                        mData.add(user);
                                    }
                                }
                            }
                            else{
                                mData.add(user);
                            }

                             */
                        }
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void addControls(View view){
        recyclerView=view.findViewById(R.id.list_user_chat);
        userList=new HashSet<>();
        mReference= FirebaseDatabase.getInstance().getReference();
        mUser= FirebaseAuth.getInstance().getCurrentUser();

        mData=new ArrayList<>();
        adapter=new UserAdapter(getContext(),mData,this);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));
    }

    @Override
    public void onItemClick(int position) {
        Intent intent=new Intent(getContext(), ChatActivity.class);
        intent.putExtra("userId",mData.get(position).getId());
        startActivity(intent);
    }

    @Override
    public void onItemLongClick(int position) {

    }
}
