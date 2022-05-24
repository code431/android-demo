package com.example.buttonnavigationdemo;

import androidx.lifecycle.ViewModelProviders;

import android.animation.ObjectAnimator;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class FirstFragment extends Fragment {

    private FirstViewModel mViewModel;
    private ImageView imageView;

    public static FirstFragment newInstance() {
        return new FirstFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.first_fragment, container, false);
        imageView = view.findViewById(R.id.imageView);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(FirstViewModel.class);
        imageView.setRotation(mViewModel.rotationPosition);
        final ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(imageView,"rotation",0,0);//动画对象，动画属性，从哪个值到哪个值
        objectAnimator.setDuration(500);//设置动画时间单位毫秒
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!objectAnimator.isRunning()){//动画完成后才可以再次点击
                    objectAnimator.setFloatValues(imageView.getRotation(),imageView.getRotation() + 100);//每次点击旋转100°
                    mViewModel.rotationPosition += 100;
                    objectAnimator.start();
                }
            }
        });
    }

}
