package com.stecon.patipan_on.diarycar;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionMenu;

public class PriceAllActivity2 extends AppCompatActivity {

    private FloatingActionMenu floatingActionMenu;
    private com.github.clans.fab.FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price_all2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Price All");
        toolbar.setSubtitle("Test");

        setSupportActionBar(toolbar);

//        floatingActionMenu = (FloatingActionMenu) findViewById(R.id.fab_menu);
//        floatingActionMenu.setClosedOnTouchOutside(true);
//
//        floatingActionButton = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.menu_item);
//        floatingActionButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(PriceAllActivity2.this, "Test", Toast.LENGTH_SHORT).show();
//                floatingActionMenu.close(true);
//            }
//        });
    }

}
