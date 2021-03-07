package com.example.chapter3.homework;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    private TextView mTextView;
    private ImageView imageView;

    public CardViewHolder(@NonNull View itemView) {
        super(itemView);
        mTextView = itemView.findViewById(R.id.TextInCard);
        imageView=itemView.findViewById(R.id.imageView);
        imageView.setImageResource(R.mipmap.ic_launcher);
    }

    public void bind(String text) {
        mTextView.setText(text);
    }

    @Override
    public void onClick(View v) {
        System.out.println("test");
    }
}
