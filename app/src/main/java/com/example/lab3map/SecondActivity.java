package com.example.lab3map;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;
import java.util.List;

public class SecondActivity extends AppCompatActivity implements GmapFragment.Fragment1Listener, Fragment2.Fragment2Listener{
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private GmapFragment fragment1;
    private Fragment2 fragment2;
    private MenuFragment fragment3;
    private CartFragment fragment4;
    private MeFragment fragment5;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;
    private FirebaseAuth mAuth;
    private static String TAG = "ABC";



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        setNavDrawer();
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        fragment1 = new GmapFragment();


        setViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

        fragment2 = Fragment2.newInstance();
        fragment3 = MenuFragment.newInstance();
        fragment4 = CartFragment.newInstance();
        fragment5 = MeFragment.newInstance();

    }

    private void setViewPager(ViewPager viewPager) {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(fragment1, "Search");
        viewPagerAdapter.addFragment(Fragment2.newInstance(), "List");
        viewPagerAdapter.addFragment(MenuFragment.newInstance(),  "Menu");
        viewPagerAdapter.addFragment(CartFragment.newInstance(), "Cart");
        viewPagerAdapter.addFragment(MeFragment.newInstance(), "Me");
        viewPager.setAdapter(viewPagerAdapter);

    }


    @Override
    public void onInputFragment1Sent(List<HashMap<String, String>> hashMaps) {
        fragment2.updateRank(hashMaps);
    }

    @Override
    public void onInputFragment2Sent(List<HashMap<String, String>> hashMaps) {

    }

    private void setNavDrawer(){

        toolbar = (Toolbar) findViewById(R.id.tooBar);
        setSupportActionBar(toolbar);
        mAuth = FirebaseAuth.getInstance();
        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.nav_view);
        toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

                                                             // This method will trigger on item Click of navigation menu
          @Override
           public boolean onNavigationItemSelected(MenuItem menuItem) {

              if (menuItem.isChecked()) menuItem.setChecked(false);
              else menuItem.setChecked(true);
                drawerLayout.closeDrawers();
//
//              FragmentManager fm = getSupportFragmentManager();
//              FragmentTransaction ft = fm.beginTransaction();
//              Fragment f = null;
                  switch (menuItem.getItemId()) {
                   case R.id.logout:
                       FirebaseAuth.getInstance().signOut();
                       startActivity(new Intent(getApplicationContext(), MainActivity.class));
                       finish();
                       break;
//                   case R.id.me:
//                         f = new GmapFragment();
//                         // FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//
//                          break;
                   default:
                          break;
                  }
//              ft.replace(R.id.viewPager,f);
//              ft.commit();
              return true;
             }
        });
    }
}