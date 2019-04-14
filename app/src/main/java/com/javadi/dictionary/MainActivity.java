package com.javadi.dictionary;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import database.DBHelper;

public class MainActivity extends AppCompatActivity {

    List<String> words=new ArrayList<>();
    Toolbar toolbar;
    ImageView imgPronounce,imgMenu;
    DrawerLayout drawerLayout;
    AutoCompleteTextView actvMainPage;
    ConstraintLayout clExitMenu,clHistoryMenu;
    TextView tvPersianMenu;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //init views
        toolbar=(Toolbar)findViewById(R.id.toobar);
        drawerLayout=(DrawerLayout)findViewById(R.id.drawer);
        imgMenu=(ImageView)findViewById(R.id.img_menu_history_page);
        imgPronounce=(ImageView)findViewById(R.id.img_pronounce_history_page);
        actvMainPage=(AutoCompleteTextView)findViewById(R.id.autoCompleteTextView_main_page);
        clExitMenu=(ConstraintLayout)findViewById(R.id.menu_exit);
        tvPersianMenu=(TextView)findViewById(R.id.tv_persian_main_page);
        clHistoryMenu=(ConstraintLayout)findViewById(R.id.menu_history);

        words=App.dbHelper.wordList();

        //set toolbar
        setSupportActionBar(toolbar);

        //set array adapter for autocompletetextview
        ArrayAdapter arrayAdapter=new ArrayAdapter(MainActivity.this,android.R.layout.simple_list_item_1,words);
        actvMainPage.setAdapter(arrayAdapter);

        //hide keyboard at startup
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        clickManager();

        //menu drawer manager
        if(drawerLayout.isDrawerOpen(Gravity.RIGHT)){
            drawerLayout.closeDrawer(Gravity.RIGHT);
        }

        actvMainPage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tvPersianMenu.setText(App.dbHelper.translate(actvMainPage.getText().toString().toLowerCase()));
                hideKeyboard();
            }
        });
        actvMainPage.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    tvPersianMenu.setText(App.dbHelper.translate(actvMainPage.getText().toString().toLowerCase()));
                    hideKeyboard();
                    handled = true;
                }
                return handled;
            }
        });


    }

    //hide keyboard
    private void hideKeyboard(){
        InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }

    private void clickManager(){
        imgMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(drawerLayout.isDrawerOpen(Gravity.RIGHT)){
                    drawerLayout.closeDrawer(Gravity.RIGHT);
                }
                else{
                    drawerLayout.openDrawer(Gravity.RIGHT);
                }
            }
        });

        clExitMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        clHistoryMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent historyIntent=new Intent(MainActivity.this,History.class);
                startActivity(historyIntent);
                if(drawerLayout.isDrawerOpen(Gravity.RIGHT)){
                    drawerLayout.closeDrawer(Gravity.RIGHT);
                }
            }
        });

        imgPronounce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
