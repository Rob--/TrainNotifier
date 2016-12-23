package io.github.rob__.trainnotifier.Search;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.rob__.trainnotifier.API.Models.API;
import io.github.rob__.trainnotifier.API.Models.Journey;
import io.github.rob__.trainnotifier.API.Models.Leg;
import io.github.rob__.trainnotifier.CustomListeners;
import io.github.rob__.trainnotifier.R;
import io.github.rob__.trainnotifier.Utils;

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.ViewHolder> {

    private static CustomListeners.JourneyClickListener clickListener;
    private static API journeys;
    private static Context context;

    /* track the view positions that we've completed animations for so we don't keep repeating
       animations as the user scrolls up and down and views reload */
    private static List<Integer> animCompleted;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tvRowArrive) public TextView tvArrive;
        @BindView(R.id.tvRowDepart) public TextView tvDepart;
        @BindView(R.id.tvRowRoute) public TextView tvRoute;
        @BindView(R.id.tvRowPlatform) public TextView tvPlatform;
        @BindView(R.id.tvRowChanges) public TextView tvChanges;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    clickListener.journeyClicked(journeys.getJourneys().get(position), position);
                }
            });
        }

        public void setArrivalTime(String time){
            tvArrive.setText(time);
        }

        public void setDepartureTime(String time){
            tvDepart.setText(time);
        }

        public void setRoute(String station){
            tvRoute.setText(context.getString(R.string.route, station));
        }

        public void setPlatform(String platform){
            if (platform != null && !platform.isEmpty()) {
                tvPlatform.setText(context.getString(R.string.platform, platform));
            } else {
                tvPlatform.setText("");
            }
        }

        public void setChanges(int changes){
            if(changes == 0) {
                tvChanges.setText(R.string.direct);
            } else {
                tvChanges.setText(context.getResources().getQuantityString(R.plurals.changes, changes));
            }
        }

        public void playAnimation(boolean alreadyAnimated, int position){
            itemView.setTranslationY(Utils.getScreenHeight(context));
            itemView
                    .animate()
                    .translationY(0)
                    .setInterpolator(new DecelerateInterpolator(1.f))
                    .setDuration(alreadyAnimated ? 0 : 500)
                    .setStartDelay(alreadyAnimated ? 0 : (100 * position)).start();
        }
    }

    public SearchResultAdapter(API journeys, Context context, CustomListeners.JourneyClickListener clickListener) {
        SearchResultAdapter.journeys = journeys;
        SearchResultAdapter.context = context;
        SearchResultAdapter.clickListener = clickListener;
        animCompleted = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.train_search_result, null);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Journey journey = journeys.getJourneys().get(position);

        List<Leg> legs = journey.getLegs();

        String arriveTime = Utils.getFormattedTime(journey.getArrivalDateTime());
        String departTime = Utils.getFormattedTime(legs.get(0).getOrigin().getScheduledTime());

        /* returning here will probably leave views as default layout (TODO: look into) */
        if (arriveTime == null || departTime == null) return;

        holder.setArrivalTime(arriveTime);
        holder.setDepartureTime(departTime);

        String destinationCode = legs.get(0).getDestination().getStationCode();
        holder.setRoute(Utils.stationFromCode(destinationCode));

        String platform = legs.get(0).getOrigin().getPlatform();
        holder.setPlatform(platform);

        int changes = journey.getLegs().size() - 1;
        holder.setChanges(changes);

        boolean alreadyAnimated = animCompleted.indexOf(position) > -1;

        /* animate elements entering the list */
        holder.playAnimation(alreadyAnimated, position);

        if(!alreadyAnimated) animCompleted.add(position);
    }

    @Override
    public int getItemCount() {
        return journeys == null ? 0 : journeys.getJourneys().size();
    }
}
