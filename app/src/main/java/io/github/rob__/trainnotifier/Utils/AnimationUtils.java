package io.github.rob__.trainnotifier.Utils;

import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.RelativeLayout;

import java.util.ArrayList;

/* http://stackoverflow.com/a/31720191/5631268 */
public class AnimationUtils {
    private static final ArrayList<Integer> collapsedViews = new ArrayList<>();

    public static boolean collapsedBefore(View v){
        for(int i = 0; i < collapsedViews.size(); i++){
            if(collapsedViews.get(i) == v.getId()) return true;
        }
        return false;
    }

    public static void collapseView(final View v) {
        collapsedViews.add(v.getId());
        final int initialHeight = v.getMeasuredHeight();

        Animation collapseAnim = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if(interpolatedTime == 1){
                    v.setVisibility(View.GONE);
                } else {
                    v.getLayoutParams().height = initialHeight - (int)(initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        collapseAnim.setInterpolator(new AccelerateInterpolator(1.f));
        collapseAnim.setDuration((int)(initialHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(collapseAnim);
    }

}
