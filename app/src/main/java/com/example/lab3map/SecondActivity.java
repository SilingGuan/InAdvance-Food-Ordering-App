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
    private CartFragment fragment3;
    private MeFragment fragment4;
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
        fragment3 = CartFragment.newInstance();
        fragment4 = MeFragment.newInstance();

    }


    private void setViewPager(ViewPager viewPager) {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(fragment1, "Search");
        viewPagerAdapter.addFragment(Fragment2.newInstance(), "List");
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


          @Override
           public boolean onNavigationItemSelected(MenuItem menuItem) {
              int THE_POSITION = 0;

              if (menuItem.isChecked()) menuItem.setChecked(false);
              else menuItem.setChecked(true);
                drawerLayout.closeDrawers();


                  switch (menuItem.getItemId()) {
                   case R.id.logout:
                      // viewPager.setCurrentItem(0);
                       FirebaseAuth.getInstance().signOut();
                       startActivity(new Intent(getApplicationContext(), MainActivity.class));
                       finish();
                       break;
                   case R.id.me:
                       THE_POSITION = 04;
                      // viewPager.setCurrentItem(4);
                       addFragmentToStack(new MeFragment());
                          break;
                   default:
                          break;
                  }
              viewPager.setCurrentItem(THE_POSITION);
              //viewPager.setCurrentItem(tab.getPosition());
              return true;
             }
        });
    }
//    public void onBackPressed() { // This code loads home fragment when back key is pressed // when user is in other fragment than home
//        if (shouldLoadHomeFragOnBackPress) {
//            // checking if user is on other navigation menu // rather than home
//            if (navItemIndex != 0) { navItemIndex = 0; CURRENT_TAG = TAG_HOME; loadHomeFragment(); return; } } super.onBackPressed(); }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.me) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private void addFragmentToStack(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.viewPager, fragment).commit();

    }
}