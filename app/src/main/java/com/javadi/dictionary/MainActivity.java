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
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    List<String> words=new ArrayList<>();
    List<String> historyWords=new ArrayList<>();
    List<String> favoriteWords=new ArrayList<>();
    Toolbar toolbar;
    ImageView imgPronounce,imgMenu,imgFavorite;
    DrawerLayout drawerLayout;
    AutoCompleteTextView actvMainPage;
    ConstraintLayout clExitMenu,clHistoryMenu,clFavorite,clMainMenu;
    TextView tvPersianMainPage;
    TextToSpeech t1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //init views
        toolbar=(Toolbar)findViewById(R.id.toobar_history);
        drawerLayout=(DrawerLayout)findViewById(R.id.drawer);
        imgMenu=(ImageView)findViewById(R.id.img_menu_history_page);
        imgPronounce=(ImageView)findViewById(R.id.img_pronounce_history_page);
        actvMainPage=(AutoCompleteTextView)findViewById(R.id.autoCompleteTextView_main_page);
        imgFavorite=(ImageView)findViewById(R.id.img_favorite);
        clExitMenu=(ConstraintLayout)findViewById(R.id.menu_exit);
        clFavorite=(ConstraintLayout)findViewById(R.id.menu_favorite);
        clMainMenu=(ConstraintLayout)findViewById(R.id.menu_main_page);
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
                checkHistoryContainer();
                if(checkfavoriteContainer()==true){
                    imgFavorite.setImageResource(R.drawable.favorite_fill);
                    imgFavorite.setTag("true");
                }
                else {
                    imgFavorite.setImageResource(R.drawable.favorite_border);
                    imgFavorite.setTag("false");
                }
            }
        });
        actvMainPage.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    if(App.dbHelper.translate(actvMainPage.getText().toString().toLowerCase())!=null){
                        tvPersianMainPage.setText(App.dbHelper.translate(actvMainPage.getText().toString().toLowerCase()));
                        hideKeyboard();
                        checkHistoryContainer();
                        if(checkfavoriteContainer()==true){
                            imgFavorite.setImageResource(R.drawable.favorite_fill);
                            imgFavorite.setTag("true");
                        }
                        else {
                            imgFavorite.setImageResource(R.drawable.favorite_border);
                            imgFavorite.setTag("false");
                        }
                    }
                    else{
                        hideKeyboard();
                        Toast.makeText(MainActivity.this,"لغتی یافت نشد",Toast.LENGTH_SHORT).show();
                    }
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
                imgFavorite.setImageResource(R.drawable.favorite_border);
                imgFavorite.setTag("false");
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

        clMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(drawerLayout.isDrawerOpen(Gravity.RIGHT)){
                    drawerLayout.closeDrawer(Gravity.RIGHT);
                }
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

        clFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent favoriteIntent=new Intent(MainActivity.this,Favorite.class);
                startActivity(favoriteIntent);
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

        imgFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!tvPersianMainPage.getText().toString().equals("") && !actvMainPage.getText().toString().equals("")){
                    if(checkfavoriteContainer()==false){

                        if(App.dbHelper.insertFavoriteWord(actvMainPage.getText().toString())!=-1){
                            Toast.makeText(MainActivity.this,"لغت به مورد علاقه ها افزوده شد",Toast.LENGTH_SHORT).show();
                            imgFavorite.setImageResource(R.drawable.favorite_fill);
                            imgFavorite.setTag("true");
                        }
                    }
                    else {
                        if(App.dbHelper.deleteFavoriteWord(actvMainPage.getText().toString())!=-1){
                            Toast.makeText(MainActivity.this,"لغت از مجموعه مورد علاقه ها حذف شد",Toast.LENGTH_SHORT).show();
                        }
                        imgFavorite.setImageResource(R.drawable.favorite_border);
                        imgFavorite.setTag("false");
                    }
                }
                else {
                    Toast.makeText(MainActivity.this,"لغتی یافت نشد",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        actvMainPage.dismissDropDown();
    }

    private void checkHistoryContainer(){
        historyWords=App.dbHelper.getHistoryList();
        if(!historyWords.contains(actvMainPage.getText().toString())){
            App.dbHelper.insertHistoryWord(actvMainPage.getText().toString());
        }
    }

    private boolean checkfavoriteContainer(){
        favoriteWords=App.dbHelper.getFavoriteList();
        if(favoriteWords.contains(actvMainPage.getText().toString())){
            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

    @Override
    protected void onStop() {
        super.onStop();
        actvMainPage.setText("");
        tvPersianMainPage.setText("فارسی");
        imgFavorite.setImageResource(R.drawable.favorite_border);
        imgFavorite.setTag("false");
    }
}
