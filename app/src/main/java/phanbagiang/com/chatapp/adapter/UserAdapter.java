package phanbagiang.com.chatapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import phanbagiang.com.chatapp.R;
import phanbagiang.com.chatapp.model.User;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    Context context;
    List<User>mData;
    IEventsRecycler iEventsRecycler;

    public UserAdapter(Context context, List<User> mData, IEventsRecycler iEventsRecycler) {
        this.context = context;
        this.mData = mData;
        this.iEventsRecycler=iEventsRecycler;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(this.context).inflate(R.layout.item_list,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user=mData.get(position);
        holder.txtName.setText(user.getName());
        if(user.getImage().equals("default")){
            holder.img.setImageResource(R.drawable.ic_launcher_background);
        }
        else{
            Glide.with(this.context)
                    .load(user.getImage())
                    .into(holder.img);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView img;
        TextView txtName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.list_image);
            txtName=itemView.findViewById(R.id.list_name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iEventsRecycler.onItemClick(getAdapterPosition());
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    iEventsRecycler.onItemLongClick(getAdapterPosition());
                    return false;
                }
            });
        }
    }
}
