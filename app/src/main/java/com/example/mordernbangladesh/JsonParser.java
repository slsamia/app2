package com.example.mordernbangladesh;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class JsonParser {

    private static final Gson gson = new Gson();

    // Class to represent Zila
    public static class Zila {
        @SerializedName("banner")
        public String banner;

        @SerializedName("id")
        public String id;

        @SerializedName("name")
        public String name;

        @SerializedName("image")
        public String image;

        @SerializedName("history")
        public String history;

        @SerializedName("geography")
        public String geography;

        @SerializedName("economy")
        public String economy;

        @SerializedName("education")
        public String education;

        // Default constructor required by Gson
        public Zila() {
        }

        public Zila(String banner, String id, String name,String image,  String history, String geography, String economy, String education) {
            this.banner = banner;
            this.id = id;
            this.image = image;
            this.name = name;
            this.history = history;
            this.geography = geography;
            this.economy = economy;
            this.education = education;
        }
    }

    // Class to represent Division
    public static class Division {
        @SerializedName("name")
        public String name;
        @SerializedName("image")
        public String image;

        @SerializedName("banner")
        public String banner;

        @SerializedName("zila")
        public Zila[] zilas;

        // Default constructor required by Gson
        public Division() {
        }

        public Division(String name, String banner,   String image, Zila[] zilas) {
            this.name = name;
            this.banner = banner;
            this.zilas = zilas;
            this.image = image;
        }
    }

    // Method to load JSON from assets using InputStreamReader for better handling
    public static String loadJSONFromAsset(Context context, String fileName) {
        try (InputStream inputStream = context.getAssets().open(fileName);
             InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8)) {

            StringBuilder stringBuilder = new StringBuilder();
            char[] buffer = new char[1024];
            int length;
            while ((length = reader.read(buffer)) != -1) {
                stringBuilder.append(buffer, 0, length);
            }
            return stringBuilder.toString();

        } catch (IOException e) {
            Log.e("JsonParser", "Error loading JSON from assets", e);
            return null;
        }
    }

    // Alternative method to directly parse from InputStream using Gson
    public static JsonObject loadJSONObjectFromAsset(Context context, String fileName) {
        try (InputStream inputStream = context.getAssets().open(fileName);
             InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8)) {

            return gson.fromJson(reader, JsonObject.class);

        } catch (IOException e) {
            Log.e("JsonParser", "Error loading JSON object from assets", e);
            return null;
        }
    }

    // Method to parse JSON and find a division by name using Gson
    public static Division getDivisionData(Context context, String divisionName) {
        try {
            // Load JSON object directly from assets
            JsonObject jsonObject = loadJSONObjectFromAsset(context, "data.json");
            if (jsonObject == null) {
                Log.e("JsonParser", "Failed to load JSON");
                return null;
            }

            // Check if the division exists
            if (!jsonObject.has(divisionName)) {
                Log.e("JsonParser", "Division " + divisionName + " not found");
                return null;
            }

            // Get the division JSON object and parse it with Gson
            JsonObject divisionJson = jsonObject.getAsJsonObject(divisionName);
            Division division = gson.fromJson(divisionJson, Division.class);

            return division;

        } catch (Exception e) {
            Log.e("JsonParser", "Error parsing JSON with Gson", e);
            return null;
        }
    }

    // Method to find zila by ID within a specific division
    public static Zila findZilaByIdInDivision(Context context, String divisionName, String zilaId) {


        Division division = getDivisionData(context, divisionName);
        if (division == null || division.zilas == null) {

            return null;
        }

        for (Zila zila : division.zilas) {
            if (zila.id != null && zila.id.equals(zilaId)) {

                return zila;
            }
        }


        return null;
    }

    // Alternative method if you want to parse the entire JSON structure at once
    // This would require a wrapper class for the entire JSON structure
    public static class DataStructure {
        // Add division names as needed, or use a Map<String, Division>
        @SerializedName("Dhaka")
        public Division dhaka;

        @SerializedName("Chittagong")
        public Division chittagong;

        @SerializedName("Rajshahi")
        public Division rajshahi;

        @SerializedName("Khulna")
        public Division khulna;

        @SerializedName("Barisal")
        public Division barisal;

        @SerializedName("Sylhet")
        public Division sylhet;

        @SerializedName("Rangpur")
        public Division rangpur;

        @SerializedName("Mymensingh")
        public Division mymensingh;
    }

    // Method to parse entire JSON structure at once
    public static DataStructure getAllDivisionsData(Context context) {
        try (InputStream inputStream = context.getAssets().open("data.json");
             InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8)) {

            return gson.fromJson(reader, DataStructure.class);

        } catch (IOException e) {
            Log.e("JsonParser", "Error loading all divisions data", e);
            return null;
        }
    }
    // Class to hold division name and banner
    public static class DivisionSummary {
        public String name;
        public String image;


        public DivisionSummary(String name, String image) {
            this.name = name;
            this.image = image;

        }
    }
    // Method to get all division names and banners as a list
    public static ArrayList<DivisionSummary> getAllDivisionSummaries(Context context) {
        ArrayList<DivisionSummary> summaries = new ArrayList<>();

        DataStructure data = getAllDivisionsData(context);
        if (data == null) return summaries;

        if (data.dhaka != null) summaries.add(new DivisionSummary(data.dhaka.name, data.dhaka.image));
        if (data.chittagong != null) summaries.add(new DivisionSummary(data.chittagong.name, data.chittagong.image));
        if (data.rajshahi != null) summaries.add(new DivisionSummary(data.rajshahi.name, data.rajshahi.image));
        if (data.khulna != null) summaries.add(new DivisionSummary(data.khulna.name, data.khulna.image));
        if (data.barisal != null) summaries.add(new DivisionSummary(data.barisal.name, data.barisal.image));
        if (data.sylhet != null) summaries.add(new DivisionSummary(data.sylhet.name, data.sylhet.image));
        if (data.rangpur != null) summaries.add(new DivisionSummary(data.rangpur.name, data.rangpur.image));
        if (data.mymensingh != null) summaries.add(new DivisionSummary(data.mymensingh.name, data.mymensingh.image));

        return summaries;
    }

    // Example usage in an Activity
    public static void exampleUsage(Context context) {
        String divisionName = "Dhaka";
        Division division = getDivisionData(context, divisionName);
        if (division != null) {
            Log.d("JsonParser", "Division Name: " + division.name);
            Log.d("JsonParser", "Banner: " + division.banner);
            if (division.zilas != null) {
                for (Zila zila : division.zilas) {
                    Log.d("JsonParser", "Zila: " + zila.name + ", ID: " + zila.id);
                }
            }
        } else {
            Log.e("JsonParser", "Failed to retrieve division data");
        }
    }

    // Example usage for getting all divisions at once
    public static void exampleUsageAllDivisions(Context context) {
        DataStructure allData = getAllDivisionsData(context);
        if (allData != null) {
            if (allData.dhaka != null) {
                Log.d("JsonParser", "Dhaka Division: " + allData.dhaka.name);
            }
            if (allData.chittagong != null) {
                Log.d("JsonParser", "Chittagong Division: " + allData.chittagong.name);
            }
            // ... check other divisions as needed
        }
    }
}