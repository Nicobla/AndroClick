package com.example.androclick;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ProgressBar;


public class MakeTacos extends Fragment {

    private ViewPager viewPager;
    private ViewPagerAdapter pagerAdapter;

    private ProgressBar progressBar;

    public MakeTacos() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.make_tacos_fragment, container, false);

        ((MyApplication) this.getActivity().getApplicationContext()).uncheckAllIngredients();

        this.progressBar = view.findViewById(R.id.progress_bar);

        Bundle bundle = new Bundle();
        int numRecette = ((MyApplication) getActivity().getApplicationContext()).getListeRecettes().size() +1;
        bundle.putSerializable("recette", new Recette("Recette "+numRecette));

        viewPager = view.findViewById(R.id.pager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrollStateChanged(int arg0) {}

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {}

            @Override
            public void onPageSelected(int pos) {
                updateProgressBar((pos+1)*20);
            }
        });

        pagerAdapter = new ViewPagerAdapter(getChildFragmentManager(), bundle);
        viewPager.setAdapter(pagerAdapter);

        return view;
    }

    private void updateProgressBar(int progression) {
        ProgressBarAnimation anim = new ProgressBarAnimation(progressBar, progressBar.getProgress(), progression);
        anim.setDuration(250);
        progressBar.startAnimation(anim);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

}

class ProgressBarAnimation extends Animation {
    private ProgressBar progressBar;
    private float from;
    private float  to;

    public ProgressBarAnimation(ProgressBar progressBar, float from, float to) {
        super();
        this.progressBar = progressBar;
        this.from = from;
        this.to = to;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);
        float value = from + (to - from) * interpolatedTime;
        progressBar.setProgress((int) value);
    }

}