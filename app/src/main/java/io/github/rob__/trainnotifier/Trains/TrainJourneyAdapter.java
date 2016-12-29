package io.github.rob__.trainnotifier.Trains;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.rob__.trainnotifier.API.Models.Mobile.Journey;
import io.github.rob__.trainnotifier.API.Models.Mobile.Leg;
import io.github.rob__.trainnotifier.CustomListeners;
import io.github.rob__.trainnotifier.R;
import io.github.rob__.trainnotifier.Utils.Utils;

public class TrainJourneyAdapter extends RecyclerView.Adapter<TrainJourneyAdapter.ViewHolder> {

    /* we will use this adapter for listing search results and showing journeys the
       user has saved, these two contexts require subtle differences in the layout/display
       so when creating the adapter we pass which context we're using to track the changes
       that need to be made */
    public static final int SAVED_JOURNEY_ADAPTER = 0;
    public static final int SEARCH_RESULT_ADAPTER = 1;

    private final CustomListeners.JourneyClickListener clickListener;
    private final Journey[] journeys;
    private final Context context;
    private final int adapterContext;

    /* track the view positions that we've completed animations for so we don't keep repeating
       animations as the user scrolls up and down and views reload */
    private final List<Integer> animCompleted;

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tvArriveTime) public TextView tvArriveTime;
        @BindView(R.id.tvArrivePlatform) public TextView tvArrivePlatform;
        @BindView(R.id.tvArriveStation) public TextView tvArriveStation;
        @BindView(R.id.tvDepartTime) public TextView tvDepartTime;
        @BindView(R.id.tvDepartPlatform) public TextView tvDepartPlatform;
        @BindView(R.id.tvDepartStation)public TextView tvDepartStation;
        @BindView(R.id.tvRoute) public TextView tvRoute;
        @BindView(R.id.tvChanges) public TextView tvChanges;
        @BindView(R.id.tvDuration) public TextView tvDuration;
        @BindView(R.id.ivDelete) public ImageView ivDelete;

        /* register an on click listener for the delete icon */
        @OnClick(R.id.ivDelete) void submit(){
            int position = getAdapterPosition();
            clickListener.journeyClicked(journeys[position], position);
        }

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);

            /* we only register the onClick event for the whole element if the adapter is
               being used on the search results page */
            if(adapterContext == SEARCH_RESULT_ADAPTER){
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int position = getAdapterPosition();
                        clickListener.journeyClicked(journeys[position], position);
                    }
                });
            }
        }

        public void setArrival(String time, String station, String platform){
            tvArriveTime.setText(time);
            tvArriveStation.setText(station);

            if(platform != null && !platform.isEmpty()){
                tvArrivePlatform.setText(context.getString(R.string.platform, platform));
            } else {
                tvArrivePlatform.setText("");
            }
        }

        public void setDeparture(String time, String station, String platform){
            tvDepartTime.setText(time);
            tvDepartStation.setText(station);

            if(platform != null && !platform.isEmpty()){
                tvDepartPlatform.setText(context.getString(R.string.platform, platform));
            } else {
                tvDepartPlatform.setText(context.getString(R.string.platformNull));
            }
        }

        public void setRoute(String station){
            tvRoute.setText(context.getString(R.string.route, station));
        }

        public void setDuration(String departTime, String arriveTime){
            tvDuration.setText(Utils.getDuration(departTime, arriveTime));
        }

        public void setChanges(int changes){
            if(changes == 0) {
                tvChanges.setText(context.getString(R.string.direct));
            } else {
                tvChanges.setText(context.getResources().getQuantityString(R.plurals.changes, changes, changes));
            }
        }

        public void displayDeleteIcon(boolean display){
            if(!display) ivDelete.setVisibility(View.GONE);
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

    public TrainJourneyAdapter(Journey[] journeys, int adapterContext, Context context, CustomListeners.JourneyClickListener clickListener) {
        this.journeys = journeys;
        this.context = context;
        this.clickListener = clickListener;
        this.adapterContext = adapterContext;
        animCompleted = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutType = adapterContext == SAVED_JOURNEY_ADAPTER ? R.layout.fragment_trains_list_journey : R.layout.fragment_search_list_result;
        View v = LayoutInflater.from(parent.getContext()).inflate(layoutType, null);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Journey journey = journeys[position];
        if(journey == null || journeys.length == 0) return;

        List<Leg> legs = journey.getLegs();

        String arriveTime = Utils.getFormattedTime(legs.get(legs.size() - 1).getDestination().getScheduledTime());
        String arrivePlatform = legs.get(legs.size() - 1).getDestination().getPlatform();
        String arriveStation = journey.getDestination();
        holder.setArrival(arriveTime, arriveStation, arrivePlatform);

        String departTime = Utils.getFormattedTime(legs.get(0).getOrigin().getScheduledTime());
        String departPlatform = legs.get(0).getOrigin().getPlatform();
        String departStation = journey.getOrigin();
        holder.setDeparture(departTime, departStation, departPlatform);

        String destinationCode = legs.get(0).getDestination().getStationCode();
        holder.setRoute(Utils.stationFromCode(destinationCode));

        holder.setDuration(journey.getDepartureDateTime(), journey.getArrivalDateTime());

        int changes = journey.getLegs().size() - 1;
        holder.setChanges(changes);

        holder.displayDeleteIcon(adapterContext == SAVED_JOURNEY_ADAPTER);

        boolean alreadyAnimated = animCompleted.indexOf(position) > -1;

        /* animate elements entering the list */
        holder.playAnimation(alreadyAnimated, position);

        if(!alreadyAnimated) animCompleted.add(position);
    }

    @Override
    public int getItemCount() {
        return journeys == null ? 0 : journeys.length;
    }
}
