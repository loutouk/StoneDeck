package com.example.louis.stonedeck;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;

import pl.droidsonroids.gif.GifTextView;

public class MainActivity extends AppCompatActivity {

    private Button deckButton;
    private Button cartesButton;
    private GifTextView gifWallpaper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        deckButton = (Button) findViewById(R.id.deckButton);
        cartesButton = (Button) findViewById(R.id.carteButton);
        gifWallpaper = (GifTextView) findViewById(R.id.gifWallpaper);

        deckButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(MainActivity.this, DeckActivity.class);
                MainActivity.this.startActivity(myIntent);
            }
        });

        cartesButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(MainActivity.this, CartesActivity.class);
                MainActivity.this.startActivity(myIntent);
            }
        });

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (hasFocus) {
            // Check if we're running on Android 5.0 or higher
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                // Apply activity transition
                launchTransition();
            }
        }
    }

    private void launchTransition() {
        // Gif wallpaper roation
        RotateAnimation rotate = new RotateAnimation(0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        rotate.setDuration(1000);
        gifWallpaper.setAnimation(rotate);
    }
}
