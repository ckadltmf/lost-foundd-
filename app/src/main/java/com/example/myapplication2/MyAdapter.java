package com.example.myapplication2;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

        ArrayList<Items> DataArrayList= new ArrayList<>();
        Context context;
        ArrayList<Items> profiles;

        public MyAdapter(Context c , ArrayList<Items> p)
        {
            context = c;
            profiles = p;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_card_view,parent,false));
        }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            holder.item.setText(profiles.get(position).getObjectTitle());
            holder.description.setText(profiles.get(position).getDescription());
            Picasso.get().load(profiles.get(position).getImg()).into(holder.profilePic);
            holder.btn.setVisibility(View.VISIBLE);
            holder.onClick(position);

        DataArrayList.add(profiles.get(position));
        }

        @Override
        public int getItemCount() {
            return profiles.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder
        {
            TextView item,description;
            ImageView profilePic;
            Button btn;
            public MyViewHolder(View itemView) {
                super(itemView);
                item = (TextView) itemView.findViewById(R.id.Item);
                description = (TextView) itemView.findViewById(R.id.Description);
                profilePic = (ImageView) itemView.findViewById(R.id.profilePic);
                btn = (Button) itemView.findViewById(R.id.checkDetails);
            }
            public void onClick(final int position)
            {
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context, position+" is clicked", Toast.LENGTH_SHORT).show();
                        Intent intent= new Intent(context, ViewPost.class);
                        intent.putExtra("ObjectTitle", DataArrayList.get(position).getObjectTitle());
                        intent.putExtra( "description", DataArrayList.get(position).getDescription());
                        intent.putExtra( "img", DataArrayList.get(position).getImg());
                        intent.putExtra( "date", DataArrayList.get(position).getDate());
                        intent.putExtra( "status", DataArrayList.get(position).getStatus());
                        intent.putExtra( "place", DataArrayList.get(position).getPlace());
                        intent.putExtra( "UserID", DataArrayList.get(position).getUserID());
                        intent.putExtra( "category", DataArrayList.get(position).getCategory());
                        intent.putExtra( "happend", DataArrayList.get(position).getHappend());

                        context.startActivity(intent);
                    }
                });
            }
        }
    }