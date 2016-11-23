package com.example.android.musicalive;

import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class UploadSong extends AppCompatActivity {

    private static final int PICKFILE_RESULT_CODE = 1001 ;
    private StorageReference mStorageRef;
    Button openButton;
    Button upload;
    TextView path;
    TextView foundpath;
    String FilePath = "";
    FirebaseStorage firebaseStorage;
    String pathstr = "fsgjbv";
    String Songpath = "?";
    private String[] mPath;
    private String[] mMusic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_song);
        openButton = (Button)findViewById(R.id.button2);
        upload  = (Button)findViewById(R.id.btnupload);
        path = (TextView)findViewById(R.id.textView);
        foundpath = (TextView)findViewById(R.id.pathfound);
        mStorageRef = FirebaseStorage.getInstance().getReference();


//        Metadata metadata = {
//                contentType: 'image/jpeg',
//        };

//        foundpath.setText(mMusic[7]);


        openButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("audio/mpeg");
                startActivityForResult(intent,PICKFILE_RESULT_CODE);


//                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
//                intent.addCategory(Intent.CATEGORY_OPENABLE);
//                intent.setType("*/*");
//                startActivityForResult(intent, PICKFILE_RESULT_CODE);




            }
        });

        if(FilePath == "")
        {
            // Toast.makeText(MainActivity.this,"nothing",Toast.LENGTH_LONG).show();
        }
        else
        {

        }


        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //   String nnn = "/storage/emulated/0/Sounds/Voice 001.amr";
                Uri file = Uri.fromFile(new File(FilePath));
                StorageReference riversRef = mStorageRef.child(FilePath);
                riversRef.putFile(file);

                //    Toast.makeText(MainActivity.this,Info,Toast.LENGTH_LONG).show();

            }
        });



    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
// TODO Auto-generated method stub
        Uri selectedImageUri = data.getData();
        String[] projection = {MediaStore.Audio.Media.DISPLAY_NAME};
        switch(requestCode){
            case PICKFILE_RESULT_CODE:
                if(resultCode==RESULT_OK){
//                  FilePath = data.getData().getPath();



//try{
                    Cursor cursor = getContentResolver().query(selectedImageUri, projection, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(projection[0]);
                    String picturePath = cursor.getString(columnIndex);
                    cursor.close();
                    FilePath = getFirebaseURIparth(picturePath);
                    // Toast.makeText(this,FilePath,Toast.LENGTH_LONG).show();

//}
//catch (Exception e)
//{}



                }
                break;

        }
    }



    public String pathEditor(String path)
    {

        int l = path.length();
        int r = l - 5;
        path = path.substring(0,r);

        return path;
    }


    //get all the songs Display names
    private String[] getAudioList()
    {
        final Cursor mCursor = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Audio.Media.DISPLAY_NAME,MediaStore.Audio.Media.DATA},null,null
                ,"LOWER("+MediaStore.Audio.Media.TITLE+")ASC");

        int count = mCursor.getCount();
        String[] songs = new String[count];
        String[] mAudioparth = new String[count];

        int i = 0;
        if(mCursor.moveToFirst())
        {
            do{songs[i]=mCursor.getString(mCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME));
                mAudioparth[i]=mCursor.getString(mCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
                i++;
            }while (mCursor.moveToNext());

        }
        mCursor.close();
        return songs;

    }

    //Get all paths of songs from phone
    private String[] getmAudioPath() {
        final Cursor mCursor = getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                new String[] { MediaStore.Audio.Media.DISPLAY_NAME, MediaStore.Audio.Media.DATA }, null, null,
                "LOWER(" + MediaStore.Audio.Media.TITLE + ") ASC");

        int count = mCursor.getCount();

        String[] songs = new String[count];
        String[] path = new String[count];
        int i = 0;
        if (mCursor.moveToFirst())
        {
            do
            {
                songs[i] = mCursor.getString(mCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME));
                path[i] = mCursor.getString(mCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
                i++;
            } while (mCursor.moveToNext());
        }

        mCursor.close();

        return path;
    }


    public String getFirebaseURIparth(String songname)
    {
        String uriPath = "null";

        mPath = getmAudioPath();
        mMusic = getAudioList();
        int i = mMusic.length;

        for(int k = 0;k < i;k++)
        {
            if(mMusic[k].equals(songname))
            {
                uriPath = mPath[k].toString();
                Toast.makeText(this,uriPath,Toast.LENGTH_SHORT).show();
                break;
            }

        }

        return uriPath;
    }

}


