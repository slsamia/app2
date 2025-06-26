package com.example.mordernbangladesh;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

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
        if(intent.hasExtra("zilaId")) {
            this.setTitle(intent.getStringExtra("zilaId"));
        }

        String zilaId = intent.getStringExtra("zilaId");
        String divisionName = intent.getStringExtra("divisionName");

        JsonParser.Zila zila = JsonParser.findZilaByIdInDivision(this, divisionName, zilaId);

        if(zila == null) return;
        TextView districtHistory = findViewById(R.id.districtHistory);
        TextView districtGeography = findViewById(R.id.districtGeography);
        TextView districtEconomy = findViewById(R.id.districtEconomy);
        TextView districtEducations = findViewById(R.id.districtEducations);

        districtHistory.setText(zila.history);
        districtGeography.setText(zila.geography);
        districtEconomy.setText(zila.economy);
        districtEducations.setText(zila.education);

    }
}