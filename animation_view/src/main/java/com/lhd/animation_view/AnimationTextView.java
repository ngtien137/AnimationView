package com.lhd.animation_view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.AnimRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

public class AnimationTextView extends AppCompatTextView implements LifecycleObserver {

    private LifecycleOwner viewLifecycleOwner;
    private Animation currentAnimation;
    private int currentAnimationResource = -1;
    private boolean isBoundToLifeCycle = false;
    private boolean isStartWhenReady = true;

    public AnimationTextView(@NonNull Context context) {
        super(context);
        initView(context, null);
    }

    public AnimationTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public AnimationTextView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(@NonNull Context context, @Nullable AttributeSet attrs) {
        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.AnimationAttrs);
            currentAnimationResource = ta.getResourceId(R.styleable.AnimationAttrs_animation_resource, -1);
            if (currentAnimationResource != -1) {
                initAnimWithResource(currentAnimationResource);
            }
            isBoundToLifeCycle = ta.getBoolean(R.styleable.AnimationAttrs_animation_bind_to_life_cycle, false);
            isStartWhenReady = ta.getBoolean(R.styleable.AnimationAttrs_animation_start_when_ready, true);
            if (isBoundToLifeCycle) {
                setBindToLifecycleOfContext();
            }
            ta.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        post(new Runnable() {
            @Override
            public void run() {
                if (isStartWhenReady)
                    startAnimation(currentAnimationResource, isBoundToLifeCycle);
            }
        });
    }

    public void bindToLifecycle(@NonNull LifecycleOwner viewLifecycleOwner) {
        if (this.viewLifecycleOwner != null)
            this.viewLifecycleOwner.getLifecycle().removeObserver(this);
        this.viewLifecycleOwner = viewLifecycleOwner;
        viewLifecycleOwner.getLifecycle().addObserver(this);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    private void onLifeCyclePause() {
        stopCurrentAnimation();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private void onLifeCycleResume() {
        startCurrentAnimation();
    }

    public void initAnimWithResource(int resAnim) {
        currentAnimationResource = resAnim;
        currentAnimation = AnimationUtils.loadAnimation(getContext(), resAnim);
    }

    public void startAnimation(int resAnim, boolean isBindToLifecycle) {
        initAnimWithResource(resAnim);
        if (currentAnimation != null) {
            startAnimation(currentAnimation);
        }
        if (isBindToLifecycle) {
            setBindToLifecycleOfContext();
        }
    }

    private void setBindToLifecycleOfContext() {
        if (getContext() instanceof AppCompatActivity) {
            bindToLifecycle((LifecycleOwner) getContext());
        }
    }

    public void startCurrentAnimation() {
        if (currentAnimation != null)
            startAnimation(currentAnimation);
    }

    public void stopCurrentAnimation() {
        if (currentAnimation != null)
            currentAnimation.cancel();
    }
}
