package com.example.android.musicalive;

import android.app.Activity;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
import android.widget.Spinner;
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
import java.util.ArrayList;

import static java.security.AccessController.getContext;

public class MusicListFrag extends Fragment implements AdapterView.OnItemSelectedListener{

    private static final int RC_SIGN_IN = 9001;
    private static final String TAG = "GoogleActivity";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthList;
    private GoogleApiClient mGoogleApiClient;
    private int count = 0;
    FirebaseUser user;
    String username;
    String usermail;
    String[] language;
    String[] lang;
    Spinner spinnerCat;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.activity_music_list_frag, parent, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {


        spinnerCat = (Spinner)view.findViewById(R.id.CatSpinner);



        //Google Sign in

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

//        mGoogleApiClient = new GoogleApiClient.Builder(getContext())
//                .enableAutoManage(getActivity() /* FragmentActivity */, (GoogleApiClient.OnConnectionFailedListener) getActivity() /* OnConnectionFailedListener */)
//                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
//                .build();
//

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



        //---------------------------------------





        String[] category = {"R&B","Hip Hop","House"};

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,category);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCat.setAdapter(arrayAdapter);


        String cat = spinnerCat.getSelectedItem().toString();


            spinnerCat.setOnItemSelectedListener(this);





        language = new String[]{"Dumela","ke teng","Ke phela hantle",
                "Ke thabile / hloname","Nka tjhentjha tjhelete hokae?",
                "Nomoro ya kamore ke mang?","Na nka kopa ho sheba menyu?",
                "Le bula neng / le kwala neng?",
                "Re ya kae?","Ntumelle ke tsamaye!"};


        lang = new String[]{"Hello","I am fine","I am happy / sad","Where can I get money changed?",
                "What is the room number?",
                "Please may I look at the menu?","When do you open / close?"
                ,"Can I use your phone / computer?","Where are we going?",
                "Let me go!"};

        int count = lang.length;

        ArrayList<ClassForAd> songs = new ArrayList<ClassForAd>();

        for (int i = 0;i < count;i++)
        {
            songs.add(new ClassForAd(lang[i],language[i]));
        }


        AdapterList adapterList = new AdapterList((Activity) getContext(),songs,R.color.colorNumbers);
        ListView listView = (ListView)view.findViewById(R.id.numberslist);
        listView.setAdapter(adapterList);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {

                Intent intent = new Intent(getActivity(),MusicPlayer.class);
                startActivity(intent);
            }

        });

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {

        String cat;
        cat = spinnerCat.getSelectedItem().toString();
        switch (pos)
        {
            case 0:

                Toast.makeText(getContext(),cat,Toast.LENGTH_LONG).show();
                break;
            case 1:

                Toast.makeText(getContext(),cat,Toast.LENGTH_LONG).show();
                break;
            case 2:

                Toast.makeText(getContext(),cat,Toast.LENGTH_LONG).show();
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
