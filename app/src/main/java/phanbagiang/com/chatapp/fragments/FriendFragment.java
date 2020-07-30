package phanbagiang.com.chatapp.fragments;

import android.content.Intent;
import android.os.Bundle;
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
import java.util.List;

import phanbagiang.com.chatapp.R;
import phanbagiang.com.chatapp.activities.ChatActivity;
import phanbagiang.com.chatapp.adapter.IEventsRecycler;
import phanbagiang.com.chatapp.adapter.UserAdapter;
import phanbagiang.com.chatapp.model.User;

public class FriendFragment extends Fragment implements IEventsRecycler {
    RecyclerView recyclerView;
    List<User>mData;
    UserAdapter adapter;

    DatabaseReference reference;
    FirebaseUser firebaseUser;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_friend,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView=view.findViewById(R.id.list_user);
        mData=new ArrayList<>();
        adapter=new UserAdapter(getContext(),mData,this);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        reference= FirebaseDatabase.getInstance().getReference("users");
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        loadData();
    }
    private void loadData(){
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mData.clear();
                for(DataSnapshot data:dataSnapshot.getChildren()){
                    User user=data.getValue(User.class);
                    if(!user.getId().equals(firebaseUser.getUid())){
                        mData.add(user);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), ""+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onItemClick(int position) {
        Intent intent=new Intent(getContext(), ChatActivity.class);
        intent.putExtra("user",mData.get(position));
        startActivity(intent);
    }

    @Override
    public void onItemLongClick(int position) {

    }
}
