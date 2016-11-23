package com.example.android.musicalive;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by Codetribe on 2016/11/17.
 */

public class AdapterList extends ArrayAdapter<ClassForAd> {


    private  int backGROUNDcol;
    public AdapterList(Activity context,ArrayList<ClassForAd> words,int backgroundColor)
    {
        super(context,0,words);

        backGROUNDcol = backgroundColor;
    }


    @Override
    public View getView(int pos,View convertView, ViewGroup parent)
    {
        View listview = convertView;

        if(listview == null)
        {
            listview = LayoutInflater.from(getContext()).inflate(R.layout.listview_custom,parent,false);
        }

        ClassForAd forAd = getItem(pos);

        TextView defualt = (TextView)listview.findViewById(R.id.default_textview);
        defualt.setText(forAd.getmDefault());

        ImageView imageView = (ImageView)listview.findViewById(R.id.imageView);
        if(forAd.hasimage())
        {
            imageView.setVisibility(View.VISIBLE);
            imageView.setImageResource(forAd.getImageResource());
        }
else {
            imageView.setVisibility(View.GONE);
        }

        //Background color
        //_________________ get access to the layout
        View textContainer = listview.findViewById(R.id.text_container);
        //_________________ get color set from an activity
        int color = ContextCompat.getColor(getContext(),backGROUNDcol);
        //_________________ setting color
        textContainer.setBackgroundColor(color);

        return listview;
    }
}
