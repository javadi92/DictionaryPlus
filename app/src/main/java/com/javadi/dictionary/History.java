package com.javadi.dictionary;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;

import com.javadi.dictionary.adapter.HistoryAdapter;
import java.util.ArrayList;
import java.util.List;

public class History extends AppCompatActivity {

    RecyclerView recyclerViewHistory;
    HistoryAdapter historyAdapter;
    List<String> words=new ArrayList<>();
    ImageView imgMenuHistoryPage;
    DrawerLayout drawerLayoutHistory;
    ConstraintLayout clMainPage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        imgMenuHistoryPage=(ImageView)findViewById(R.id.img_menu_history_page);
        clMainPage=(ConstraintLayout)findViewById(R.id.menu_main_page);
        recyclerViewHistory=(RecyclerView)findViewById(R.id.recy_history);
        drawerLayoutHistory=(DrawerLayout)findViewById(R.id.drawer_history);
        LinearLayoutManager llm=new LinearLayoutManager(getBaseContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewHistory.setLayoutManager(llm);
        words=App.dbHelper.getHistoryList();
        historyAdapter=new HistoryAdapter(History.this,words);
        recyclerViewHistory.setAdapter(historyAdapter);

        imgMenuHistoryPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(drawerLayoutHistory.isDrawerOpen(Gravity.RIGHT)){
                    drawerLayoutHistory.closeDrawer(Gravity.RIGHT);
                }
                else {
                    drawerLayoutHistory.openDrawer(Gravity.RIGHT);
                }
            }
        });
        clMainPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
