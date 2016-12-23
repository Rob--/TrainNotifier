package io.github.rob__.trainnotifier;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.rob__.trainnotifier.Search.SearchFragment;
import io.github.rob__.trainnotifier.Trains.TrainsFragment;

public class MainActivity extends FragmentActivity {

    private final Fragment[] fragments = new Fragment[] {
        SearchFragment.newInstance(),
        TrainsFragment.newInstance()
    };

    @BindView(R.id.viewPager)
    ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        pager.setAdapter(new CustomPagerAdapter(getSupportFragmentManager()));

        /* set an `onTrainSaved` listener on the search fragment, when the user saves a train
           savedJourney it will call the `trainSaved` method inside the train fragment which will
           call upon the presenter to update the view */
        ((SearchFragment) fragments[0]).setOnTrainSavedListener(new CustomListeners.TrainSavedListener() {
            @Override
            public void trainSaved() {
                ((TrainsFragment) fragments[1]).trainSaved();
            }
        });

    }

    private class CustomPagerAdapter extends FragmentPagerAdapter {

        public CustomPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int pos) {
            return fragments[pos];
        }

        @Override
        public int getCount() {
            return fragments.length;
        }
    }

}
