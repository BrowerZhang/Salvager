package com.example.salvager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    String[] searches = {"milk container", "egg carton", "water bottle"};

    ArrayAdapter<String> arrayAdapter;


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

                Toast.makeText(MainActivity.this, "This item is: " + arrayAdapter.getItem(position), Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.searches,menu);

        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Type to search your recyclable item");

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