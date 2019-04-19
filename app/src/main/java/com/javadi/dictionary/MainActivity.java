package com.javadi.dictionary;

import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.support.constraint.ConstraintLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    List<String> words=new ArrayList<>();
    Toolbar toolbar;
    ImageView imgPronounce,imgMenu;
    DrawerLayout drawerLayout;
    AutoCompleteTextView actvMainPage;
    ConstraintLayout clExitMenu,clHistoryMenu;
    TextView tvPersianMainPage;
    TextToSpeech t1;

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
        tvPersianMainPage =(TextView)findViewById(R.id.tv_persian_main_page);
        clHistoryMenu=(ConstraintLayout)findViewById(R.id.menu_history);
        t1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.UK);
                }
            }
        });

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
                tvPersianMainPage.setText(App.dbHelper.translate(actvMainPage.getText().toString().toLowerCase()));
                hideKeyboard();
            }
        });
        actvMainPage.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    tvPersianMainPage.setText(App.dbHelper.translate(actvMainPage.getText().toString().toLowerCase()));
                    hideKeyboard();
                    actvMainPage.dismissDropDown();
                }
                return false;
            }
        });

        actvMainPage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tvPersianMainPage.setText("");
            }

            @Override
            public void afterTextChanged(Editable s) {

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
                if(actvMainPage.getText().toString().trim()!=""){
                    t1.speak(actvMainPage.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        actvMainPage.dismissDropDown();
    }
}
