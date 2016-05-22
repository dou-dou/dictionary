package com.example.dictionary.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.dictionary.R;
import com.example.dictionary.util.HttpCallbackListener;
import com.example.dictionary.util.HttpUtil;
import com.example.dictionary.util.JsonUtility;

import org.json.JSONException;
import java.util.Map;

public class MainActivity extends FragmentActivity implements NavigationView.OnNavigationItemSelectedListener {
    private ImageButton imageButton_search;
    private EditText editText_search;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        imageButton_search = (ImageButton) findViewById(R.id.button_search);
        editText_search = (EditText) findViewById(R.id.edit_search);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        final ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        InitUI();

        navigationView.setNavigationItemSelectedListener(this);

        imageButton_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String et_word = editText_search.getText().toString();

                if (!et_word.trim().isEmpty()) {
                    android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.replace(R.id.framement_alternative, search_show_Fragment.newInstance(et_word));
                    transaction.commit();
                }
            }
        });

        editText_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (editText_search.getText().toString().trim().isEmpty()) {
                    search_Fragment _searchFragment = new search_Fragment();
                    android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.replace(R.id.framement_alternative, _searchFragment);
                    transaction.commit();
                }
            }
        });

    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        android.support.v4.app.FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();

        if (id == R.id.nav_search) {
            InitUI();
        } else if (id == R.id.nav_sentence) {

        } else if (id == R.id.nav_rawbook) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void InitUI(){
        search_Fragment fragment_show=new search_Fragment();
        android.support.v4.app.FragmentManager init_fragmentManager=getSupportFragmentManager();
        FragmentTransaction init_transaction=init_fragmentManager.beginTransaction();
        init_transaction.replace(R.id.framement_alternative,fragment_show);
        init_transaction.commit();

    }
}
