package com.javadi.dictionary.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.javadi.dictionary.R;

import java.util.ArrayList;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.myViewHolder> {

    List<String> words=new ArrayList<>();
    Context mContext;

    public HistoryAdapter(Context context,List<String> words){
        this.words=words;
        this.mContext=context;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.history_view_holder,viewGroup,false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder myViewHolder, int i) {
        myViewHolder.tvEnglishHistory.setText(words.get(i));
    }

    @Override
    public int getItemCount() {
        return words.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder{

        TextView tvEnglishHistory;
        ImageView imgPronunceHistory;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            tvEnglishHistory=(TextView)itemView.findViewById(R.id.tv_english_history);
            imgPronunceHistory=(ImageView)itemView.findViewById(R.id.img_pronunce_history);
        }
    }
}
