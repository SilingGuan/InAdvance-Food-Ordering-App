package com.example.lab3map;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Nullable;

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
    FirebaseAuth mAuth;
    FirebaseFirestore firestore;
    StorageReference storageReference;
    private static String TAG = "ABC";
    TextView fullName;
    ImageView profileImage;
    String userID;
    Uri imageUri;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        setNavDrawer();
        StorageReference profileRef = storageReference.child("users/"+mAuth.getCurrentUser().getUid()+"profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profileImage);
            }
        });

        tabLayout =  findViewById(R.id.tabLayout);
        viewPager =  findViewById(R.id.viewPager);

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

        toolbar = findViewById(R.id.tooBar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.nav_view);
        toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();



        View header = navigationView.getHeaderView(0);
        profileImage = header.findViewById(R.id.profpic);


        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open gallery
                Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGalleryIntent,1000);
            }
        });



         // setup drawer - display the user's full name
        fullName = header.findViewById(R.id.header_fName);
        userID = mAuth.getCurrentUser().getUid();
        DocumentReference documentReference = firestore.collection("users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                String name = documentSnapshot.getString("fullName");
                fullName.setText(" Welcome！ "+name);
            }
        });



        // drawer- select menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {


          @Override
           public boolean onNavigationItemSelected(MenuItem menuItem) {
              //int THE_POSITION = 0;

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
                    case R.id.login:
                          // viewPager.setCurrentItem(0);
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(getApplicationContext(), SignUpActivity.class));
                        finish();
                        break;
                   case R.id.me:
                       //THE_POSITION = 04;
                       viewPager.setCurrentItem(4);
                       addFragmentToStack(new MeFragment());
                          break;
                   default:
                          break;
                  }
             // viewPager.setCurrentItem(THE_POSITION);
              //viewPager.setCurrentItem(tab.getPosition());
              return true;
             }
        });


    }
//    @Override
//    public void onBackPressed() {
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
//    }
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @androidx.annotation.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000 && resultCode == RESULT_OK ){
//            if(resultCode == Activity.RESULT_OK){
                imageUri = data.getData();
               // profileImage.setImageURI(imageUri);

                uploadImageToFirebase();
            }
        }


    private void uploadImageToFirebase() {
        // upload image to firebase storage
        final StorageReference fileRef = storageReference.child("users/"+mAuth.getCurrentUser().getUid()+"profile.jpg");
        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
               // Toast.makeText(SecondActivity.this,"Image Uploaded", Toast.LENGTH_SHORT).show();
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(profileImage);
                    }
                });
            }
        })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SecondActivity.this, "Failed.",Toast.LENGTH_SHORT).show();
            }
        });
    }
}