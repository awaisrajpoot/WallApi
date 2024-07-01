package com.example.wallapi;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.librarywall.AppConstants;
import com.example.librarywall.WallpaperStarter;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements WallpaperStarter.WallpaperListener {

    WallpaperStarter wallpaperStarter;

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

        wallpaperStarter = new WallpaperStarter(this, AppConstants.webPrefix);
        wallpaperStarter.setStart(AppConstants.rootMetaLink);
    }

    @Override
    public void onDataLoaded(ArrayList<String> nameList, ArrayList<String> countList) {

        Log.e("loaded", "server response is : " + nameList.toString());
        Log.e("loaded", "server response is : " + countList.toString());

        int catIndex = 1;

        ArrayList<String> dataList = wallpaperStarter
                .getSingleItems(nameList.get(catIndex), countList.get(catIndex));

        for (String address:dataList){
            Log.i("address",nameList.get(catIndex) + " : address is : "+address);
        }
    }
}