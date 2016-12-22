package io.github.rob__.trainnotifier;

import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.RelativeLayout;

import java.util.ArrayList;

/* http://stackoverflow.com/a/31720191/5631268 */
public class AnimationUtils {
    static ArrayList<Integer> collapsedViews = new ArrayList<>();

    public static boolean collapsedBefore(View v){
        for(int i = 0; i < collapsedViews.size(); i++){
            if(collapsedViews.get(i) == v.getId()) return true;
        }
        return false;
    }

    public static void expandView(final View v) {
        v.measure(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        final int targetHeight = v.getMeasuredHeight();

        v.getLayoutParams().height = 0;
        v.setVisibility(View.VISIBLE);

        Animation expandAnim = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                /* wrap_content will return normal height of the view */
                v.getLayoutParams().height = interpolatedTime == 1 ? RelativeLayout.LayoutParams.WRAP_CONTENT : (int)(targetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        expandAnim.setInterpolator(new AccelerateInterpolator(1.f));
        expandAnim.setDuration((int)(targetHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(expandAnim);
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
