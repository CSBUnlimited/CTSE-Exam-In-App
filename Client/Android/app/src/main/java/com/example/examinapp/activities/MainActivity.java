package com.example.examinapp.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.TextView;

import com.example.examinapp.R;
import com.example.examinapp.enums.NextScreenEnum;
import com.example.examinapp.enums.UserTypeEnum;
import com.example.examinapp.models.UserModel;
import com.example.examinapp.viewmodels.MainActivityViewModel;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private MainActivityViewModel _mainActivityViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        _mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        _mainActivityViewModel.init();

        UserModel userModel = _mainActivityViewModel.getLoggedInUserModel();

        ((TextView)navigationView.getHeaderView(0).findViewById(R.id.peronNameTextView)).setText(userModel.getName());
        ((TextView)navigationView.getHeaderView(0).findViewById(R.id.peronUserTypeTextView)).setText(userModel.getUserTypeEnum().toString());

        navigationView.getMenu().setGroupVisible(R.id.lectureMenuGroup, (userModel.getUserTypeEnum() == UserTypeEnum.Lecturer));
        navigationView.getMenu().setGroupVisible(R.id.studentMenuGroup, (userModel.getUserTypeEnum() == UserTypeEnum.Student));

//        View view = findViewById(R.id.includeViewMain);
//        view.setVisibility(View.VISIBLE);

        _mainActivityViewModel.getNextScreenEnum().observe(this, new Observer<NextScreenEnum>() {
            @Override
            public void onChanged(@Nullable NextScreenEnum nextScreenEnum) {
                if (nextScreenEnum == NextScreenEnum.Splash) {
                    Intent intent = new Intent(MainActivity.this, SplashActivity.class);
                    MainActivity.this.startActivity(intent);
                    finish();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            _mainActivityViewModel.logoutLoggedInUser();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_lec_myExams) {

        }
        else if (id == R.id.nav_lec_allExams) {

        }
        else if (id == R.id.nav_stu_allExams) {

        }
        else if (id == R.id.nav_stu_entrolledExams) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
