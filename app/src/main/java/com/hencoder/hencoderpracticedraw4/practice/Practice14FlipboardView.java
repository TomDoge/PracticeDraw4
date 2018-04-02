package com.hencoder.hencoderpracticedraw4.practice;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.util.AttributeSet;
import android.view.View;

import com.hencoder.hencoderpracticedraw4.R;

public class Practice14FlipboardView extends View {
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Bitmap bitmap;
    Camera camera = new Camera();
    private int degreeZ;
    private int degreeY;
    private int offsetDegreeY;
    private AnimatorSet mAnimatorSet;

    public Practice14FlipboardView(Context context) {
        super(context);
    }

    public Practice14FlipboardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice14FlipboardView(Context context, @Nullable AttributeSet attrs, int
            defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.maps);

        ObjectAnimator animator1 = ObjectAnimator.ofInt(this, "degreeY", 0, -45);
        animator1.setDuration(1000);
        animator1.setStartDelay(500);
        ObjectAnimator animator2 = ObjectAnimator.ofInt(this, "degreeZ", 0, -270);
        animator2.setDuration(1000);
        animator2.setInterpolator(new FastOutSlowInInterpolator());
        animator2.setStartDelay(500);
        ObjectAnimator animator3 = ObjectAnimator.ofInt(this, "offsetDegreeY", 0, 45);
        animator3.setDuration(1000);
        animator3.setStartDelay(500);
        mAnimatorSet = new AnimatorSet();
        mAnimatorSet.playSequentially(animator1, animator2, animator3);
        mAnimatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                reset();
                animation.start();
            }
        });
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mAnimatorSet.start();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mAnimatorSet.cancel();
    }

    public void reset() {
        degreeZ = 0;
        degreeY = 0;
        offsetDegreeY = 0;
        invalidate();
    }

    @SuppressWarnings("unused")
    public void setDegreeY(int degreeY) {
        this.degreeY = degreeY;
        invalidate();
    }

    @SuppressWarnings("unused")
    public void setOffsetDegreeY(int offsetDegreeY) {
        this.offsetDegreeY = offsetDegreeY;
        invalidate();
    }

    @SuppressWarnings("unused")
    public void setDegreeZ(int degreeZ) {
        this.degreeZ = degreeZ;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int bitmapWidth = bitmap.getWidth();
        int bitmapHeight = bitmap.getHeight();
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        int x = centerX - bitmapWidth / 2;
        int y = centerY - bitmapHeight / 2;

        canvas.save();
        canvas.translate(centerX, centerY);
        canvas.rotate(degreeZ);
        canvas.clipRect(0, -bitmapHeight, bitmapWidth, bitmapHeight);
        camera.save();
        camera.rotateY(degreeY);
        camera.applyToCanvas(canvas);
        camera.restore();
        canvas.rotate(-degreeZ);
        canvas.translate(-centerX, -centerY);
        canvas.drawBitmap(bitmap, x, y, paint);
        canvas.restore();

        canvas.save();
        canvas.translate(centerX, centerY);
        canvas.rotate(degreeZ);
        canvas.clipRect(-bitmapWidth, -bitmapHeight, 0, bitmapHeight);
        camera.save();
        camera.rotateY(offsetDegreeY);
        camera.applyToCanvas(canvas);
        camera.restore();
        canvas.rotate(-degreeZ);
        canvas.translate(-centerX, -centerY);
        canvas.drawBitmap(bitmap, x, y, paint);
        canvas.restore();
    }
}
