package com.example.salvager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    String[] searches = {"Water Bottles", "Milk Jug", "Detergent Bottles", "Plastic Shopping Bags", "Ketchup Bottles", "Plastic Egg Carton", "Baby Bottles",
            "Glass Bottle", "Batteries + Automotive Batteries", "Cardboard", "Soup Can", "Scrap Metal", "Paint"};

    ArrayAdapter<String> arrayAdapter;


    BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //AppCompatDelegate.setDefaultNightMode();

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
                        fragment = new FragmentMap();
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

        listView = findViewById(R.id.listview);

        arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,searches);
        listView.setAdapter(arrayAdapter);
        listView.setVisibility(View.INVISIBLE);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                if(arrayAdapter.getItem(position).equalsIgnoreCase(searches[0]))
                {
                    startActivity(new Intent(MainActivity.this, ActivityWaterBottle.class));
                }
                else if (arrayAdapter.getItem(position).equalsIgnoreCase(searches[1]))
                {
                    startActivity(new Intent(MainActivity.this, ActivityMilkCarton.class));
                }
                else if (arrayAdapter.getItem(position).equalsIgnoreCase(searches[2]))
                {
                    startActivity(new Intent(MainActivity.this, ActivityDetergentBottles.class));
                }
                else if (arrayAdapter.getItem(position).equalsIgnoreCase(searches[3]))
                {
                    startActivity(new Intent(MainActivity.this, ActivityShoppingBag.class));
                }
                else if (arrayAdapter.getItem(position).equalsIgnoreCase(searches[4]))
                {
                    startActivity(new Intent(MainActivity.this, ActivityKetchupBottle.class));
                }
                else if (arrayAdapter.getItem(position).equalsIgnoreCase(searches[5]))
                {
                    startActivity(new Intent(MainActivity.this, ActivityEggCarton.class));
                }
                else if (arrayAdapter.getItem(position).equalsIgnoreCase(searches[6]))
                {
                    startActivity(new Intent(MainActivity.this, ActivityBabyBottle.class));
                }
                else if (arrayAdapter.getItem(position).equalsIgnoreCase(searches[7]))
                {
                    startActivity(new Intent(MainActivity.this, ActivityGlassBottle.class));
                }
                else if (arrayAdapter.getItem(position).equalsIgnoreCase(searches[8]))
                {
                    startActivity(new Intent(MainActivity.this, ActivityBattery.class));
                }
                else if (arrayAdapter.getItem(position).equalsIgnoreCase(searches[9]))
                {
                    startActivity(new Intent(MainActivity.this, ActivityCardboard.class));
                }
                else if (arrayAdapter.getItem(position).equalsIgnoreCase(searches[10]))
                {
                    startActivity(new Intent(MainActivity.this, ActivitySoupCan.class));
                }
                else if (arrayAdapter.getItem(position).equalsIgnoreCase(searches[11]))
                {
                    startActivity(new Intent(MainActivity.this, ActivityScrapMetal.class));
                }
                else if (arrayAdapter.getItem(position).equalsIgnoreCase(searches[12]))
                {
                    startActivity(new Intent(MainActivity.this, ActivityPaint.class));
                }
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.searches,menu);

        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Search your recyclable item");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                listView.setVisibility(View.VISIBLE);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                arrayAdapter.getFilter().filter(newText);
                listView.setVisibility(View.INVISIBLE);
                return false;
            }

        });

        return super.onCreateOptionsMenu(menu);
    }
}