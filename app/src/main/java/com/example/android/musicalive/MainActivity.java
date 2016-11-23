package com.example.android.musicalive;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private GoogleApiClient mGoogleApiClient;
    ImageView imageNEV;
    FragmentManager mFragmentMgr;
    FirebaseUser user;
    FragmentTransaction mTransF;
    String username;
    String usermail;
    TextView name;
    TextView email;
    private int counnt = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


      //  out();
        //To check if user is signed in or out
        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {


            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if (user != null) {
                    user = firebaseAuth.getCurrentUser();
                    // User is signed in
                    username = user.getDisplayName();
                    usermail = user.getEmail();
                    Toast.makeText(MainActivity.this, "Hello " + user.getDisplayName(), Toast.LENGTH_LONG).show();
                } else {
                    // User is signed out

                }
                // ...
            }
        };


        //----------------------------------------------------------------------------------------------

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
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
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    //Auth still
    @Override
    public void onStart() {
        super.onStart();
        //adding FireBaseAuth
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        //Removing FireBaseAuth
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }


    //----------------------------------------------------------------------------------------------

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

        if(user != null)
        { name = (TextView) findViewById(R.id.usernametxt);
            email = (TextView) findViewById(R.id.useremailtxt);
            name.setText(username);
            email.setText(usermail);

            imageNEV = (ImageView)findViewById(R.id.nevImage);
            String uriImage = String.valueOf(user.getPhotoUrl());

            Picasso(imageNEV,uriImage);
        }


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
        if (id == R.id.action_settings) {

            try {
                signOut();
                counnt = 1;

                Toast.makeText(MainActivity.this, "now signed out", Toast.LENGTH_LONG).show();
            }
            catch (Exception e)
            {
                Toast.makeText(MainActivity.this, "you are already signed out", Toast.LENGTH_LONG).show();
            }



            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public int out()
    {
        return counnt;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.


        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Show login fragment
            try {
                mTransF = getSupportFragmentManager().beginTransaction();
                mTransF.replace(R.id.your_placeholder, new Foofragment());
                mTransF.commit();
            }
            catch (Exception e)
            {}


        } else if (id == R.id.nav_gallery) {

            try {
                mTransF = getSupportFragmentManager().beginTransaction();
                mTransF.replace(R.id.your_placeholder, new MusicListFrag());
                mTransF.commit();
            }
            catch (Exception e)
            {}


        } else if (id == R.id.nav_slideshow) {

//            Intent intent = new Intent(MainActivity.class,UploadSong.class);
//            startActivity(intent);


        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void Picasso(ImageView img, String uripath) {
        Picasso picasso = new Picasso.Builder(MainActivity.this).listener(new Picasso.Listener() {
            @Override
            public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {

            }
        }).build();

        if(img != null)
        {  picasso.load(uripath).into(img, new Callback() {
            @Override
            public void onSuccess() {
                Toast.makeText(MainActivity.this, "Image yes", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError() {

            }
        });
        }

    }


    private void signOut() {
        // Firebase sign out
        mAuth.signOut();

        // Google sign out
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {

            }
        });
    }
}
