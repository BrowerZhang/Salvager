package com.example.salvager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new FragmentHome()).commit();

        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;

                switch (item.getItemId())
                {
                    case R.id.ic_home:
                        fragment = new FragmentHome();
                        break;
                }
                switch (item.getItemId())
                {
                    case R.id.ic_scanner:
                        fragment = new FragmentScanner();
                        break;
                }
                switch (item.getItemId())
                {
                    case R.id.ic_search:
                        fragment = new FragmentSearch();
                        break;
                }
                switch (item.getItemId())
                {
                    case R.id.ic_map:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new Fragment()).commit();
                        break;
                }
                switch (item.getItemId())
                {
                    case R.id.ic_faq:
                        fragment = new FragmentFAQ();
                        break;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fragment).commit();

                return true;
            }
        });
    }
}