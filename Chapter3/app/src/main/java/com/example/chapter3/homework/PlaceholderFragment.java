package com.example.chapter3.homework;


import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;

import java.util.ArrayList;
import java.util.List;

public class PlaceholderFragment extends Fragment {
    private RecyclerView recyclerView;
    private LottieAnimationView lottieAnimationView;
    private  ListAdapter listAdapter=new ListAdapter();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_placeholder, container, false);
        recyclerView=view.findViewById(R.id.friend_list);
        lottieAnimationView=view.findViewById(R.id.animation_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(listAdapter);
        recyclerView.setAlpha(0.0f);
        final List<String> items = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            items.add("friend " + i);
        }
        listAdapter.notifyItems(items);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getView().postDelayed(new Runnable() {
            @Override
            public void run() {
                // 这里会在 5s 后执行
                ObjectAnimator objectAnimator_lottie=ObjectAnimator.ofFloat(lottieAnimationView,"alpha",1.0f,0.0f);
                objectAnimator_lottie.setDuration(2000);

                ObjectAnimator objectAnimator_list=ObjectAnimator.ofFloat(recyclerView,"alpha",0.0f,1.0f);
                objectAnimator_list.setDuration(2000);

                AnimatorSet animatorSet=new AnimatorSet();
                animatorSet.playTogether(objectAnimator_list,objectAnimator_lottie);
                animatorSet.start();
            }
        }, 5000);
    }
}
