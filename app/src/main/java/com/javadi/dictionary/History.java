package com.javadi.dictionary;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;

import com.javadi.dictionary.adapter.HistoryAdapter;
import java.util.ArrayList;
import java.util.List;

public class History extends AppCompatActivity {

    RecyclerView recyclerViewHistory;
    HistoryAdapter historyAdapter;
    List<String> words=new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history);

        recyclerViewHistory=(RecyclerView)findViewById(R.id.recy_history);
        LinearLayoutManager llm=new LinearLayoutManager(getBaseContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewHistory.setLayoutManager(llm);
        words=App.dbHelper.getHistoryList();
        historyAdapter=new HistoryAdapter(History.this,words);
        recyclerViewHistory.setAdapter(historyAdapter);
    }
}
