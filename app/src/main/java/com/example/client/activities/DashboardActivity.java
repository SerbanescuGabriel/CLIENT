package com.example.client.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.client.R;
import com.example.client.entitymodels.user.User;
import com.example.client.entitymodels.user.UserDetails;
import com.example.client.viewmodels.UserProfileViewModel;

public class DashboardActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    SharedPreferences sp;
    SharedPreferences.Editor editor;
    Toolbar toolbar;
    TextView txtEmail, txtName;
    FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        //setContentView(R.layout.nav_header_dashboard);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fab = findViewById(R.id.idFloatingButton);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        View headerView = navigationView.getHeaderView(0);
        txtEmail=headerView.findViewById(R.id.txtDEmail);
        txtName=headerView.findViewById(R.id.txtDName);

        //setControls();

        //get user
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        //User user=intent.getParcelableExtra("user");
        User user = (User) bundle.getSerializable("user");
        setUserDetails(user);

        //Toast.makeText(getApplicationContext(), user.toString(), Toast.LENGTH_LONG).show();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);

        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
    }


    //user defined methods
    /*void setControls() {
        txtEmail = findViewById(R.id.txtDEmail);
        txtName = findViewById(R.id.txtDName);
    }*/

    void setUserDetails(User user) {
        UserDetails ud = user.getUserDetails();

        String firstName = ud.getFirstName();
        String lastName = ud.getLastName();
        String name = firstName + " " + lastName;
        String email = user.getEmail();
        txtName.setText(name);
        txtEmail.setText(email);

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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard, menu);
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        } else if (id == R.id.nav_logout) {
            sp = getSharedPreferences("userId", MODE_PRIVATE);
            editor = sp.edit();
            editor.putInt("userId", 0);
            editor.commit();
            Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
