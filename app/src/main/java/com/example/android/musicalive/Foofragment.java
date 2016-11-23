package com.example.android.musicalive;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.concurrent.Executor;

public class Foofragment extends Fragment implements
        GoogleApiClient.OnConnectionFailedListener,
        View.OnClickListener {

    private static final int RC_SIGN_IN = 9001;
    private static final String TAG = "GoogleActivity";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthList;
    private GoogleApiClient mGoogleApiClient;
    private int count = 0;
    FirebaseUser user;
    String username;
    String usermail;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.activity_foofragment, parent, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
//        Button SignIn = (Button) view.findViewById(R.id.signINbtn);
//        Button button = (Button) view.findViewById(R.id.googlebtnlog);



        view.findViewById(R.id.googlebtnlog).setOnClickListener(this);

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                .enableAutoManage(getActivity() /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        //Initialize auth
        mAuth = FirebaseAuth.getInstance();

        //listens if user is signed in or not
        mAuthList = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

              user = firebaseAuth.getCurrentUser();
                if(user != null)
                {
                    count = 0;
                   getActivity().findViewById(R.id.googlebtnlog).setClickable(false);
                    username = user.getDisplayName();
                    usermail = user.getEmail();
                   Toast.makeText(getContext(),"Welcome "+user.getDisplayName(),Toast.LENGTH_LONG).show();
                }
                else
                {
                    count++;
                    Toast.makeText(getContext(),"Please sign in",Toast.LENGTH_LONG).show();
                }
                //comment out soon
                //updateUI(user);
            }
        };


//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                signIn();
//                Toast.makeText(getContext(), "qqeqeryhtfgh.",Toast.LENGTH_SHORT).show();
//
//            }
//        });

    }

    @Override
    public void onStart()
    {
        super.onStart();
            mAuth.addAuthStateListener(mAuthList);
        
    }


    @Override
    public void onStop()
    {


        super.onStop();
        mAuth.addAuthStateListener(mAuthList);



    }


    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data )
    {
        super.onActivityResult(requestCode,resultCode,data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if(requestCode == RC_SIGN_IN)
        {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
           if(result.isSuccess()) {

           GoogleSignInAccount account = result.getSignInAccount();
         //see next method
          firebaseAuthWithGoogle(account);

           }
            else{
               //set the UI to do nothing
               //uncomment soon
               //updateUI(user);
           }
           }

    }


    private void firebaseAuthWithGoogle(GoogleSignInAccount acc)
    {


            AuthCredential credential = GoogleAuthProvider.getCredential(acc.getIdToken(),null);

try{
            mAuth.signInWithCredential(credential).addOnCompleteListener((Executor) this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    // If sign in fails, display a message to the user. If sign in succeeds
                    // the auth state listener will be notified and logic to handle the
                    // signed in user can be handled in the listener.
                    if (!task.isSuccessful())
                    {
                        Toast.makeText(getContext(), "Authentication failed.",Toast.LENGTH_SHORT).show();
                    }
                    // [START_EXCLUDE]

                    // [END_EXCLUDE]
                }
            });
}
catch (Exception e)
{}

    }


    private void signIn() {

//        try{
//
//        }
//        catch (Exception e)
//        {}
      Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
         startActivityForResult(signInIntent, RC_SIGN_IN);

    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();

        } else {
            // Signed out, show unauthenticated UI.

        }
    }

    private void revokeAccess() {
        // Firebase sign out
        mAuth.signOut();

        // Google revoke access
        Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        //updateUI(null);
                    }
                });
    }



    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.googlebtnlog) {
            signIn();
            Toast.makeText(getContext(), "qqeqeryhtfgh.",Toast.LENGTH_SHORT).show();

        }

    }




    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}