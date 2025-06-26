package com.example.mordernbangladesh;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.Objects;

public class SingleDistrict extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_single_district);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();


        String zilaId = intent.getStringExtra("zilaId");
        String divisionName = intent.getStringExtra("divisionName");

        JsonParser.Zila zila = JsonParser.findZilaByIdInDivision(this, divisionName, zilaId);

        if(zila == null) return;
        this.setTitle(zila.name);
        TextView districtHistory = findViewById(R.id.districtHistory);
        TextView districtGeography = findViewById(R.id.districtGeography);
        TextView districtEconomy = findViewById(R.id.districtEconomy);
        TextView districtEducations = findViewById(R.id.districtEducations);
        ImageView districtImage = findViewById(R.id.districtImage);


        if (!Objects.equals(zila.image, "") && zila.image != null ) {
            Glide.with(this)
                    .load(zila.image)
                    .apply(new RequestOptions())
                    .into(districtImage);
        }

        districtHistory.setText(zila.history);
        districtGeography.setText(zila.geography);
        districtEconomy.setText(zila.economy);
        districtEducations.setText(zila.education);

    }
}