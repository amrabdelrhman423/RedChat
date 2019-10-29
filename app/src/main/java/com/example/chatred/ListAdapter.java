package com.example.chatred;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.chatred.Model.Chat;
import com.example.chatred.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

   public Context context;
   public List<User> mlist;
   private boolean ischat;
   String thelastmessage;
   public ListAdapter(Context context,List<User> mlist,boolean ischat){
       this.context=context;
       this.mlist=mlist;
       this.ischat =ischat;
   }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.item_list,parent,false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

       final User user =mlist.get(position);
       holder.username.setText(user.getUsername());
       if (user.getImageUrl().equals("default")){
           holder.userimage.setImageResource(R.drawable.pic7);
       }else {
           Glide.with(context).load(user.getImageUrl()).into(holder.userimage);
       }
       if (ischat){
           lastMessage(user.getId(),holder.last_massage);
       }else {
           holder.last_massage.setVisibility(View.GONE);
       }
       if (ischat){
           if (user.getStatus().equals("online")){
               holder.online.setVisibility(View.VISIBLE);
               holder.offline.setVisibility(View.GONE);
           }else {
               holder.offline.setVisibility(View.VISIBLE);
               holder.online.setVisibility(View.GONE);
           }
       }else {
           holder.offline.setVisibility(View.GONE);
           holder.online.setVisibility(View.GONE);
       }
       holder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent =new Intent(context,MessageActivity.class);
               intent.putExtra("userid",user.getId());
               context.startActivity(intent);
           }
       });
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private CircularImageView userimage;
        private ImageView online;
        private ImageView offline;
        private TextView username,last_massage;
        private ViewHolder(@NonNull View itemView) {
            super(itemView);
            userimage =itemView.findViewById(R.id.image_data);
            username=itemView.findViewById(R.id.txt_name);
            last_massage=itemView.findViewById(R.id.txt_lmsg);
            online=itemView.findViewById(R.id.online);
            offline=itemView.findViewById(R.id.offline);
        }
    }
    private void lastMessage(final String userid, final TextView last_mesg){
       thelastmessage="default";
        final FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("chats");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot:dataSnapshot.getChildren()) {
                    Chat chat =snapshot.getValue(Chat.class);
                    if (chat.getRecvier().equals(firebaseUser.getUid())&&chat.getSende().equals(userid) ||
                    chat.getSende().equals(firebaseUser.getUid())&&chat.getRecvier().equals(userid)){
                        thelastmessage=chat.getMessage();
                    }
                }
                switch (thelastmessage){
                    case "default":
                        last_mesg.setText("No Message");
                        break;
                        default:
                            last_mesg.setText(thelastmessage);
                            break;
                }
                thelastmessage  ="default";
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
