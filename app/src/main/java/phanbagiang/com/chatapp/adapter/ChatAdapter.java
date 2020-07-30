package phanbagiang.com.chatapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import phanbagiang.com.chatapp.R;
import phanbagiang.com.chatapp.model.Chat;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    public static final int MSG_TYPE_LEFT=0;
    public static final int MSG_TYPE_RIGHT=1;
    Context mContext;
    List<Chat>mData;
    String imageUrl;
    FirebaseUser mUser;

    public ChatAdapter(Context mContext, List<Chat> mData, String imageUrl) {
        this.mContext = mContext;
        this.mData = mData;
        this.imageUrl = imageUrl;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==MSG_TYPE_RIGHT){
            LayoutInflater inflater=LayoutInflater.from(this.mContext);
            View view=inflater.inflate(R.layout.chat_item_right,parent,false);
            return new ViewHolder(view);
        }
        else{
            LayoutInflater inflater=LayoutInflater.from(this.mContext);
            View view=inflater.inflate(R.layout.chat_item_left,parent,false);
            return new ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if(imageUrl.equals("default")){
            holder.imgHinhChat.setImageResource(R.mipmap.ic_launcher);
        }
        else{
            Glide.with(this.mContext).load(imageUrl).into(holder.imgHinhChat);
        }

        holder.txtChat.setText(mData.get(position).getMessage());
    }

    @Override
    public int getItemViewType(int position) {
        mUser= FirebaseAuth.getInstance().getCurrentUser();
        if(mData.get(position).getSender().equals(mUser.getUid())){
            return MSG_TYPE_RIGHT;
        }
        else{
            return MSG_TYPE_LEFT;
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView imgHinhChat;
        TextView txtChat;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtChat=itemView.findViewById(R.id.txtChat);
            imgHinhChat=itemView.findViewById(R.id.chat_left_img);
        }
    }
}
