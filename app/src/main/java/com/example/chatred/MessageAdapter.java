package com.example.chatred;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.chatred.Model.Chat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    public static final int MSG_TYPE_LEFT=0;
    public static final int MSG_TYPE_RIGHT=1;

    private Context context;
    private List<Chat> mchat;
    private String imageUrl;

    FirebaseUser fuser;

    public MessageAdapter(Context context,List<Chat> mchat ,String imageUrl){
        this.context=context;
        this.mchat=mchat;
        this.imageUrl=imageUrl;
    }

    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType==MSG_TYPE_RIGHT) {
            View view = LayoutInflater.from(context).inflate(R.layout.chat_item_right, parent, false);
            return new MessageAdapter.ViewHolder(view);
        }else {
            View view = LayoutInflater.from(context).inflate(R.layout.chat_item_left, parent, false);
            return new MessageAdapter.ViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder holder, int position) {
        Chat chat = mchat.get(position);
        holder.show_message.setText(chat.getMessage());
        if (imageUrl.equals("default")){
            holder.profile_image.setImageResource(R.drawable.pic10);
        }else {
            Glide.with(context).load(imageUrl).into(holder.profile_image);
        }
        if (position ==mchat.size()-1){
            if (chat.isIsseen()){
                holder.txt_seen.setText("Seen");
            }else {
                holder.txt_seen.setText("Delivered");
            }
        }else {
            holder.txt_seen.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mchat.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView show_message;
        public CircularImageView profile_image;
        public  TextView txt_seen;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profile_image =itemView.findViewById(R.id.profile_image);
            show_message=itemView.findViewById(R.id.show_message);
            txt_seen=itemView.findViewById(R.id.txt_seen);

        }
    }

    @Override
    public int getItemViewType(int position) {
        fuser= FirebaseAuth.getInstance().getCurrentUser();
        if (mchat.get(position).getSende().equals(fuser.getUid())){
            return MSG_TYPE_RIGHT;
        }else {
            return MSG_TYPE_LEFT;
        }
    }
}
