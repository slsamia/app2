package com.example.mordernbangladesh;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.List;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class MainActivity extends AppCompatActivity {

    List<DivisionItem> divisionItemList = new ArrayList<>();
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

        mainDivisionListLayout = findViewById(R.id.mainDivisionListLayout);

        divisionItemList.add(new DivisionItem("Dhaka", "https://images.pexels.com/photos/32692708/pexels-photo-32692708.jpeg", "Khunla", "https://images.pexels.com/photos/27950031/pexels-photo-27950031.jpeg"));
        divisionItemList.add(new DivisionItem("Dhaka", "test", "Khunla", "test"));
        divisionItemList.add(new DivisionItem("Dhaka2", "test", "Khunla", "test"));

        for (DivisionItem div : divisionItemList) {
            View viewItem = LayoutInflater.from(this).inflate(R.layout.card_item, mainDivisionListLayout, false);

            TextView lname = viewItem.findViewById(R.id.cardLeftTitle);
            ImageView limg = viewItem.findViewById(R.id.cardLeftImage);
            TextView rname = viewItem.findViewById(R.id.cardRightTitle);
            ImageView rimg = viewItem.findViewById(R.id.cardRightImage);

            lname.setText(div.lname);
            rname.setText(div.rname);

            Glide.with(this)
                    .load(div.limage)
                    .apply(new RequestOptions())
                    .into(limg);
            Glide.with(this)
                    .load(div.rimage)
                    .apply(new RequestOptions())
                    .into(rimg);


            LinearLayout leftCard = viewItem.findViewById(R.id.cardLeft);
            LinearLayout rightCard = viewItem.findViewById(R.id.cardRight);

            leftCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, Divisions.class);
                    intent.putExtra("name", div.lname);
                    startActivity(intent);
                }
            });
            rightCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, Divisions.class);
                    intent.putExtra("name", div.rname);
                    startActivity(intent);
                }
            });

            mainDivisionListLayout.addView(viewItem);
        }


    }


}


class DivisionItem {
    String lname;
    String limage;
    String rname;
    String rimage;

    DivisionItem(String lname, String limage, String rname, String rimage) {
        this.lname = lname;
        this.rname = rname;
        this.limage = limage;
        this.rimage = rimage;
    }
}