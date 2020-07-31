package phanbagiang.com.chatapp.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import phanbagiang.com.chatapp.R;
import phanbagiang.com.chatapp.fragments.ChatFragment;
import phanbagiang.com.chatapp.fragments.FriendFragment;
import phanbagiang.com.chatapp.model.User;

public class MainActivity extends AppCompatActivity {
    CircleImageView imgUser;
    TextView txtUsername;

    Toolbar toolbar;
    DatabaseReference mReference;

    TabLayout tabLayout;
    ViewPager viewPager;

    ChatFragment chatFragment;
    FriendFragment friendFragment;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        addControls();
        addEvents();

    }
    private void addEvents(){
        imgUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,ProfileActivity.class);
                startActivity(intent);
            }
        });
    }

    private void addControls(){

        imgUser=findViewById(R.id.imgUser);
        txtUsername=findViewById(R.id.txtUserName);

        mReference=FirebaseDatabase.getInstance().getReference("users");

        tabLayout=findViewById(R.id.tabLayout);
        viewPager=findViewById(R.id.viewpager);

        ViewpagerAdaper adaper=new ViewpagerAdaper(getSupportFragmentManager());
        chatFragment=new ChatFragment();
        friendFragment=new FriendFragment();
        adaper.addFragments(chatFragment,"Chats");
        adaper.addFragments(friendFragment,"Users");
        viewPager.setAdapter(adaper);
        tabLayout.setupWithViewPager(viewPager);
        mReference.child(FirebaseAuth.getInstance().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user=dataSnapshot.getValue(User.class);
                txtUsername.setText(user.getName());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private class ViewpagerAdaper extends FragmentPagerAdapter {
        List<Fragment> fragments;
        List<String>titles;
        public ViewpagerAdaper(@NonNull FragmentManager fm) {
            super(fm);
            fragments=new ArrayList<>();
            titles=new ArrayList<>();
        }
        public void addFragments(Fragment fragment, String title){
            fragments.add(fragment);
            titles.add(title);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
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