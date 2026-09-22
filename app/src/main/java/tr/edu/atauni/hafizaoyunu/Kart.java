package tr.edu.atauni.hafizaoyunu;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.drawable.Drawable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

public class Kart extends AppCompatImageView {
    Drawable arkaPlan;
    Drawable onPlan;
    boolean acikMi = false;
    int resId;
    boolean eslesti = false;

    public Kart(Context context, int drawableResId) {
        super(context);
        this.resId = drawableResId;

        float scale = getResources().getDisplayMetrics().density;
        setCameraDistance(8000 * scale);

        int padding = (int) (scale * 4);
        setPadding(padding, padding, padding, padding);

        setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent));

        arkaPlan = ContextCompat.getDrawable(context, R.drawable.kart_arkaplan_gradient);
        onPlan = ContextCompat.getDrawable(context, drawableResId);

        setImageDrawable(arkaPlan);
        setElevation(8 * scale); // Add elevation for a floating effect
    }

    public void dondur() {
        if (eslesti && acikMi) return;

        ObjectAnimator animator1 = ObjectAnimator.ofFloat(this, "rotationY", 0f, 90f);
        animator1.setDuration(250);
        animator1.setInterpolator(new AccelerateInterpolator());

        animator1.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (acikMi) {
                    setImageDrawable(arkaPlan);
                } else {
                    setImageDrawable(onPlan);
                }
                acikMi = !acikMi;
                setRotationY(-90f);

                ObjectAnimator animator2 = ObjectAnimator.ofFloat(Kart.this, "rotationY", -90f, 0f);
                animator2.setDuration(250);
                animator2.setInterpolator(new DecelerateInterpolator());
                animator2.start();
            }
        });
        animator1.start();
    }

    public void eslestiAnimasyonu() {
        // Fade out and slightly scale down matched cards
        animate().alpha(0.6f).scaleX(0.9f).scaleY(0.9f).setDuration(500).start();
    }
}
