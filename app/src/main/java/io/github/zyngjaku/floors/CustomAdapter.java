package io.github.zyngjaku.floors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {
    private Context context;
    private ArrayList user_id, user_name, user_score, user_date;


    CustomAdapter(Context context, ArrayList user_id, ArrayList user_name, ArrayList user_score, ArrayList user_date) {
        this.context = context;
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_score = user_score;
        this.user_date = user_date;
    }

    @NonNull
    @Override
    public CustomAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.activity_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.MyViewHolder holder, int position) {
        //holder.userId_txt.setText(String.valueOf(user_id.get(position)));
        holder.name_txt.setText(String.valueOf(user_name.get(position)));
        holder.score_txt.setText(String.valueOf(user_score.get(position)));
        holder.userId_txt.setText(String.valueOf(user_date.get(position)));

    }

    @Override
    public int getItemCount() {
        return user_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView userId_txt, name_txt, score_txt;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            userId_txt = itemView.findViewById(R.id.userId_txt);
            name_txt = itemView.findViewById(R.id.name_txt);
            score_txt = itemView.findViewById(R.id.score_txt);

        }
    }
}
