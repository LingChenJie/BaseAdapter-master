package com.jiechen.example;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.jiechen.example.fragment.Example1Fragment;
import com.jiechen.example.fragment.Example2Fragment;
import com.jiechen.example.fragment.Example3Fragment;
import com.jiechen.example.fragment.Example4Fragment;
import com.jiechen.example.fragment.Example5Fragment;
import com.jiechen.example.fragment.Example6Fragment;
import com.jiechen.example.fragment.Example7Fragment;
import com.jiechen.example.fragment.HomeFragment;

public class SectionActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar mToolbar;
    private DrawerLayout mDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section);

        initView();
        initToolbar(savedInstanceState);
    }

    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    }

    private void initToolbar(Bundle savedInstanceState) {
        setSupportActionBar(mToolbar);

        // 关联DrawerLayout和toolbar
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawer, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState == null) {
            HomeFragment homeFragment = new HomeFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, homeFragment).commit();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_example1:
                replaceFragment(new Example1Fragment());
                break;

            case R.id.nav_example2:
                replaceFragment(new Example2Fragment());
                break;

            case R.id.nav_example3:
                replaceFragment(new Example3Fragment());
                break;

            case R.id.nav_example4:
                replaceFragment(new Example4Fragment());
                break;

            case R.id.nav_example5:
                replaceFragment(new Example5Fragment());
                break;

            case R.id.nav_example6:
                replaceFragment(new Example6Fragment());
                break;

            case R.id.nav_example7:
                replaceFragment(new Example7Fragment());
                break;

        }

        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
