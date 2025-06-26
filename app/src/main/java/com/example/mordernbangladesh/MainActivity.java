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

        divisionItemList.add(new DivisionItem("Dhaka", "https://cdn.britannica.com/97/189797-050-1FC0041B/Night-view-Dhaka-Bangladesh.jpg", "Khulna", "https://propertyguide.com.bd/_next/image?url=https%3A%2F%2Fpropertyguide-store.s3.ap-southeast-1.amazonaws.com%2Fbikroy%2Fmedium_Shahid_Hadis_Park_05c85ee74e.jpg&w=3840&q=75"));
        divisionItemList.add(new DivisionItem("Barisal", "https://images.pond5.com/aerial-view-barisal-city-barisal-footage-249692329_iconl.jpeg", "Chittagong ", "https://media.istockphoto.com/id/1432128033/photo/chittagong-port-city-of-bangladesh-drone-view.jpg?s=612x612&w=0&k=20&c=26sHDneb2yvW0agy15hNRuxXLkGzqd4oTJ1JdEz0SYs="));
        divisionItemList.add(new DivisionItem("Rajshahi", "https://dailyasianage.com/library/1662403733_8.jpg", "Mymensingh", "https://upload.wikimedia.org/wikipedia/commons/8/8c/Mymensingh_photo_by_Mona_Mijthab_-_02.jpg"));
        divisionItemList.add(new DivisionItem("Rangpur", "https://cdn.britannica.com/93/140293-050-09134FEF/Tajhat-Palace-Rangpur-Bangl.jpg", "Sylhet", "https://www.tbsnews.net/sites/default/files/styles/big_3/public/images/2022/01/28/sylhet_talha-chowdhury.jpg"));

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