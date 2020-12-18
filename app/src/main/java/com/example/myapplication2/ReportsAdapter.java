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

        import java.util.ArrayList;


public class ReportsAdapter extends RecyclerView.Adapter<ReportsAdapter.MyViewHolder> {

    ArrayList<Reports> reportsArrayList = new ArrayList<>();
    Context context;
    ArrayList<Reports> reports;

    public ReportsAdapter(Context c , ArrayList<Reports> p)
    {
        context = c;
        reports = p;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_card_view,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.item.setText(reports.get(position).getReportType());
        holder.description.setText(reports.get(position).getDescription());
       // Picasso.get().load(reports.get(position).getImg()).into(holder.profilePic);
        //holder.btn.setVisibility(View.VISIBLE);
        holder.onClick(position);
        reportsArrayList.add(reports.get(position));
    }

    @Override
    public int getItemCount() {
        return reports.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView item,description;
        //ImageView profilePic;
        Button btn;
        public MyViewHolder(View itemView) {
            super(itemView);
            item = (TextView) itemView.findViewById(R.id.Item);
            description = (TextView) itemView.findViewById(R.id.Description);
            btn = (Button) itemView.findViewById(R.id.checkDetails);
        }
        public void onClick(final int position)
        {
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, position+" is clicked", Toast.LENGTH_SHORT).show();
                    Intent intent= new Intent(context, ViewPost.class);
                    intent.putExtra("reportType", reportsArrayList.get(position).getReportType());
                    intent.putExtra( "description", reportsArrayList.get(position).getDescription());

                    //context.startActivity(intent);
                }
            });
        }
    }
}