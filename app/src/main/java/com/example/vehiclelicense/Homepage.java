package com.example.vehiclelicense;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Homepage extends AppCompatActivity {

    private ViewPager slideViewPager;
    private LinearLayout dotLinearLayout;

    private HomePageSliderAdapter homePageSliderAdapter;
    private TextView[] mdots;
    private Button homepage_login_btn, discover_btn, homepage_signup_btn, homepage_help_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        slideViewPager = (ViewPager) findViewById(R.id.slideViewPager);
        dotLinearLayout = (LinearLayout) findViewById(R.id.dotLinearLayout);

        homePageSliderAdapter = new HomePageSliderAdapter(this);
        slideViewPager.setAdapter(homePageSliderAdapter);

        addDotsIndicator(0);

        slideViewPager.addOnPageChangeListener(vListener);


        Window window = this.getWindow();


// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        window.setStatusBarColor(Color.TRANSPARENT);

        //initialise the login button and attach listener
        homepage_login_btn = (Button) findViewById(R.id.homepage_login_btn);
        HomePageButtonHandler handler = new HomePageButtonHandler();
        homepage_login_btn.setOnClickListener(handler);

        discover_btn = (Button) findViewById(R.id.discover_btn);
        discover_btn.setOnClickListener(handler);

        homepage_signup_btn = (Button) findViewById(R.id.homepage_signup_btn);
        homepage_signup_btn.setOnClickListener(handler);

        homepage_help_btn = (Button) findViewById(R.id.help_btn);
        homepage_help_btn.setOnClickListener(handler);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    //for the slider
    public void addDotsIndicator(int position){
        mdots = new TextView[3];
        dotLinearLayout.removeAllViews();
        for(int i = 0; i< mdots.length; i++){
            mdots[i] = new TextView(this);
            mdots[i].setText(Html.fromHtml("&#8226;"));
            mdots[i].setTextSize(35);
            mdots[i].setTextColor(getResources().getColor(R.color.TRANSPARENT_WHITE));

            dotLinearLayout.addView(mdots[i]);
        }
        if(mdots.length>0){
            mdots[position].setTextColor(getResources().getColor(R.color.WHITE));
        }
    }

    //listener for the slider
    ViewPager.OnPageChangeListener vListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDotsIndicator(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private class HomePageButtonHandler implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Intent intent = null;
            //check if user clicks the login button and open the login activity
            if(v.getId() == R.id.homepage_login_btn){
                intent = new Intent(Homepage.this, LoginActivity.class);
                startActivity(intent);
            }

            if(v.getId() == R.id.discover_btn){
                intent = new Intent(Homepage.this, Discover.class);
                startActivity(intent);
            }

            if(v.getId() == R.id.homepage_signup_btn){
                intent = new Intent(Homepage.this, SignUp.class);
                startActivity(intent);
            }
            if(v.getId() == R.id.help_btn){
                intent = new Intent(Homepage.this, HelpActivity.class);
                startActivity(intent);
            }
        }

    }

}
