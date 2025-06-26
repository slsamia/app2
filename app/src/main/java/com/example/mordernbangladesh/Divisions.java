package com.example.mordernbangladesh;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;


public class Divisions extends AppCompatActivity {
    LinearLayout mainZilaListLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_divisions);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();
        if(intent.hasExtra("name")) {
            this.setTitle(intent.getStringExtra("name"));
        };


        mainZilaListLayout = findViewById(R.id.mainZilaListLayout);
        String divisionName = intent.getStringExtra("name");

        JsonParser.Division divisionData =  JsonParser.getDivisionData(this, divisionName);

        if(divisionData == null) return;

        TextView divisionSummary = findViewById(R.id.divisionSummary);
        divisionSummary.setText(divisionData.banner);


        JsonParser.Zila a = divisionData.zilas[1];
        for(int i=0;i<divisionData.zilas.length-1;i = i+2) {

            JsonParser.Zila lZila = divisionData.zilas[i];
            JsonParser.Zila rZila = divisionData.zilas[i+1];
            View viewItem = LayoutInflater.from(this).inflate(R.layout.card_item, mainZilaListLayout, false);

            TextView lname = viewItem.findViewById(R.id.cardLeftTitle);
            ImageView limg = viewItem.findViewById(R.id.cardLeftImage);
            TextView rname = viewItem.findViewById(R.id.cardRightTitle);
            ImageView rimg = viewItem.findViewById(R.id.cardRightImage);

            lname.setText(lZila.name);
            rname.setText(rZila.name);

//            Glide.with(this)
//                    .load(div.limage)
//                    .apply(new RequestOptions())
//                    .into(limg);
//            Glide.with(this)
//                    .load(div.rimage)
//                    .apply(new RequestOptions())
//                    .into(rimg);


            LinearLayout leftCard = viewItem.findViewById(R.id.cardLeft);
            LinearLayout rightCard = viewItem.findViewById(R.id.cardRight);

            leftCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Divisions.this, SingleDistrict.class);
                    intent.putExtra("zilaId", lZila.id);
                    intent.putExtra("divisionName", divisionName);
                    startActivity(intent);
                }
            });
            rightCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Divisions.this, SingleDistrict.class);
                    intent.putExtra("zilaId", rZila.id);
                    intent.putExtra("divisionName", divisionName);
                    startActivity(intent);
                }
            });

            mainZilaListLayout.addView(viewItem);
        }

    }
}





