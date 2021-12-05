package com.example.b07_project;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.text.AttributedCharacterIterator;

public class itemDisplay extends LinearLayout {
    private TextView textName;
    private TextView textBrand;
    private TextView textQuantity;
    public itemDisplay(Context context, @Nullable AttributeSet attrs){
        super(context, attrs);
        LayoutInflater.from(getContext()).inflate(
                R.layout.display_item, this);
    }

    public void setName(String name){
        textName = (TextView) findViewById(R.id.display_name);
        textName.setTextSize(20);
        textName.setGravity(Gravity.CENTER);
        textName.setText(name);
    }

    public void setBrand(String brand){
        textBrand = (TextView)  findViewById(R.id.display_brand);
        textBrand.setGravity(Gravity.CENTER);
        textBrand.setTextSize(20);
        textBrand.setText(brand);
    }

    public void setQuantity(int quantity){
        textQuantity = (TextView) findViewById(R.id.display_cost);
        textQuantity.setGravity(Gravity.CENTER);
        textQuantity.setTextSize(20);
        textQuantity.setText(Integer.toString(quantity));
    }

}
