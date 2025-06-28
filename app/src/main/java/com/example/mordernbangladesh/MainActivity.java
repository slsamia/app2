package com.example.mordernbangladesh;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import java.util.ArrayList;
import java.util.List;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    ArrayList<JsonParser.DivisionSummary> divisionItemList = new ArrayList<>();
    LinearLayout mainDivisionListLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mainDivisionListLayout = findViewById(R.id.mainDivisionListLayout);

        divisionItemList = JsonParser.getAllDivisionSummaries(this);

        for (int i = 0; i < divisionItemList.size() - 1; i = i + 2) {
            View viewItem = LayoutInflater.from(this).inflate(R.layout.card_item, mainDivisionListLayout, false);

            TextView lname = viewItem.findViewById(R.id.cardLeftTitle);
            ImageView limg = viewItem.findViewById(R.id.cardLeftImage);
            TextView rname = viewItem.findViewById(R.id.cardRightTitle);
            ImageView rimg = viewItem.findViewById(R.id.cardRightImage);

            JsonParser.DivisionSummary left = divisionItemList.get(i);
            JsonParser.DivisionSummary right = divisionItemList.get(i + 1);

            lname.setText(left.name);
            rname.setText(right.name);

            Glide.with(this)
                    .load(left.image)
                    .apply(new RequestOptions())
                    .into(limg);
            Glide.with(this)
                    .load(right.image)
                    .apply(new RequestOptions())
                    .into(rimg);


            LinearLayout leftCard = viewItem.findViewById(R.id.cardLeft);
            LinearLayout rightCard = viewItem.findViewById(R.id.cardRight);

            leftCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, Divisions.class);
                    intent.putExtra("name", left.name);
                    startActivity(intent);
                }
            });
            rightCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, Divisions.class);
                    intent.putExtra("name", right.name);
                    startActivity(intent);
                }
            });

            mainDivisionListLayout.addView(viewItem);
        }

        setUpDrawerMenu();
    }

    private void setUpDrawerMenu() {


        drawerLayout = findViewById(R.id.main);
        navigationView = findViewById(R.id.navigation_view);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                default:
                    Toast.makeText(this, "default clicked", Toast.LENGTH_SHORT).show();
            }
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Log.v("action_menu_click", String.valueOf(id) + " == " + String.valueOf(R.id.action_about_us));
        if (id == R.id.action_about_us) {

            Intent intent = new Intent(MainActivity.this, AboutUs.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}


