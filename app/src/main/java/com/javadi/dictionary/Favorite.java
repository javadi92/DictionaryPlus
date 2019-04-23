package com.javadi.dictionary;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;

public class Favorite extends AppCompatActivity {

    RecyclerView recyFavorite;
    ImageView imgMenuFavoritePage;
    DrawerLayout drawerFavorite;
    ConstraintLayout clMainPage,clExit,clHistory;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite );

        recyFavorite=(RecyclerView)findViewById(R.id.recy_favorite);
        clMainPage=(ConstraintLayout)findViewById(R.id.menu_main_page);
        clHistory=(ConstraintLayout)findViewById(R.id.menu_history);
        clExit=(ConstraintLayout)findViewById(R.id.menu_exit);
        drawerFavorite=(DrawerLayout)findViewById(R.id.drawer_favorite);
        imgMenuFavoritePage=(ImageView)findViewById(R.id.img_menu_favorite_page);
        imgMenuFavoritePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(drawerFavorite.isDrawerOpen(Gravity.RIGHT)){
                    drawerFavorite.closeDrawer(Gravity.RIGHT);
                }
                else{
                    drawerFavorite.openDrawer(Gravity.RIGHT);
                }
            }
        });
        clMainPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentMain=new Intent(Favorite.this,MainActivity.class);
                startActivity(intentMain);
                /*if(drawerFavorite.isDrawerOpen(Gravity.RIGHT)){
                    drawerFavorite.closeDrawer(Gravity.RIGHT);
                }*/
            }
        });
        clHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentHistory=new Intent(Favorite.this,History.class);
                startActivity(intentHistory);
                if(drawerFavorite.isDrawerOpen(Gravity.RIGHT)){
                    drawerFavorite.closeDrawer(Gravity.RIGHT);
                }
            }
        });
        clExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
            }
        });
    }
}
