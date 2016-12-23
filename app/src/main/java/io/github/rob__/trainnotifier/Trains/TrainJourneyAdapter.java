package io.github.rob__.trainnotifier.Trains;

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
import butterknife.OnClick;
import io.github.rob__.trainnotifier.API.Models.Journey;
import io.github.rob__.trainnotifier.API.Models.Leg;
import io.github.rob__.trainnotifier.CustomListeners;
import io.github.rob__.trainnotifier.R;
import io.github.rob__.trainnotifier.Utils;

public class TrainJourneyAdapter extends RecyclerView.Adapter<TrainJourneyAdapter.ViewHolder> {

    private static CustomListeners.JourneyClickListener clickListener;
    private static Journey[] journeys;
    private static Context context;

    /* track the view positions that we've completed animations for so we don't keep repeating
       animations as the user scrolls up and down and views reload */
    private static List<Integer> animCompleted;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tvListArrive) public TextView tvArrive;
        @BindView(R.id.tvListArrivePlatform) public TextView tvArrivePlatform;
        @BindView(R.id.tvListArriveStation) public TextView tvArriveStation;
        @BindView(R.id.tvListDepart) public TextView tvDepart;
        @BindView(R.id.tvListDepartPlatform) public TextView tvDepartPlatform;
        @BindView(R.id.tvListDepartStation)public TextView tvDepartStation;
        @BindView(R.id.tvListRoute) public TextView tvRoute;
        @BindView(R.id.tvListChanges) public TextView tvChanges;
        @BindView(R.id.tvListDuration) public TextView tvDuration;
        final View view;

        /* register an on click listener for the delete icon */
        @OnClick(R.id.ivDelete) void submit(){
            int position = getAdapterPosition();
            clickListener.journeyClicked(journeys[position], position);
        }

        public ViewHolder(View v) {
            super(v);
            this.view = v;
            ButterKnife.bind(this, v);
        }

        public void setArrival(String time, String station, String platform){
            tvArrive.setText(time);
            tvArriveStation.setText(station);

            if(platform != null && !platform.isEmpty()){
                tvArrivePlatform.setText(context.getString(R.string.platform, platform));
            } else {
                tvArrivePlatform.setText("");
            }
        }

        public void setDeparture(String time, String station, String platform){
            tvDepart.setText(time);
            tvDepartStation.setText(station);

            if(platform != null && !platform.isEmpty()){
                tvDepartPlatform.setText(context.getString(R.string.platform, platform));
            } else {
                tvDepartPlatform.setText("");
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

    public TrainJourneyAdapter(Journey[] journeys, Context context, CustomListeners.JourneyClickListener clickListener) {
        this.journeys = journeys;
        this.context = context;
        this.clickListener = clickListener;
        this.animCompleted = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.saved_journey_card, null);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Journey journey = journeys[position];
        if(journey == null || journeys.length == 0) return;

        List<Leg> legs = journey.getLegs();

        String arriveTime = Utils.getFormattedTime(legs.get(legs.size() - 1).getDestination().getScheduledTime());
        String departTime = Utils.getFormattedTime(legs.get(0).getOrigin().getScheduledTime());

        if (arriveTime == null || departTime == null) return;

        String arrivePlatform = legs.get(legs.size() - 1).getDestination().getPlatform();
        String arriveStation = journey.getDestination();
        holder.setArrival(arriveTime, arriveStation, arrivePlatform);

        String departPlatform = legs.get(0).getOrigin().getPlatform();
        String departStation = journey.getOrigin();
        holder.setDeparture(departTime, departStation, departPlatform);

        String destinationCode = legs.get(0).getDestination().getStationCode();
        holder.setRoute(Utils.stationFromCode(destinationCode));

        holder.setDuration(journey.getDepartureDateTime(), journey.getArrivalDateTime());

        int changes = journey.getLegs().size() - 1;
        holder.setChanges(changes);

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
