package com.example.accountbook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RCVadapter extends RecyclerView.Adapter<RCVadapter.ViewHolder> {

    private ArrayList<SQLDatabase> records;
    Context context;

    public RCVadapter(ArrayList<SQLDatabase> records, Context context){
        this.records = records;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.detail_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SQLDatabase db = records.get(position);

        holder.name.setText(db.getName());
        holder.money.setText(""+db.getMoney()+"원");
        if(db.type==null){
            holder.type.setText("수입");
            holder.outcometype.setVisibility(View.INVISIBLE);
        }
        else{
            holder.type.setText("수입");
            holder.outcometype.setVisibility(View.VISIBLE);
            holder.outcometype.setText(db.getType());
        }
        int month = db.getMonth()+1;
        String date = db.getYear()+"년 "+month+"월"+db.getDay()+"일";
        holder.date.setText(date);

    }

    @Override
    public int getItemCount() {
        return records.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView name;
        TextView money;
        TextView type;
        TextView outcometype;
        TextView date;
        Button delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.detail_name);
            money = itemView.findViewById(R.id.detail_money);
            type = itemView.findViewById(R.id.detail_type);
            outcometype = itemView.findViewById(R.id.detail_outcometype);
            date = itemView.findViewById(R.id.detail_date);

            delete = itemView.findViewById(R.id.detail_delete);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //데이터 삭제
                }
            });

        }
    }
}
