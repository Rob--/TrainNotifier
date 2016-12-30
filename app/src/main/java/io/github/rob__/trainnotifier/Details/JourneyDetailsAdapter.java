package io.github.rob__.trainnotifier.Details;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.rob__.trainnotifier.API.Models.Mobile.Journey;
import io.github.rob__.trainnotifier.API.Models.Mobile.Leg;
import io.github.rob__.trainnotifier.API.Models.Realtime.RealtimeData;
import io.github.rob__.trainnotifier.API.Models.Realtime.Stop;
import io.github.rob__.trainnotifier.R;
import io.github.rob__.trainnotifier.Utils.Utils;

import static android.view.View.GONE;

public class JourneyDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_LEG = 0;
    private static final int VIEW_TYPE_CHANGE = 1;

    private final Journey journey;
    private final Context context;
    private final HashMap<String, RealtimeData> realtimeData;

    public class ViewHolderChange extends RecyclerView.ViewHolder{
        @BindView(R.id.tvChangeStation) public TextView tvChangeStation;
        @BindView(R.id.tvChangeDuration) public TextView tvChangeDuration;

        public ViewHolderChange(View v){
            super(v);
            ButterKnife.bind(this, v);
        }

        public void setStation(String station){
            tvChangeStation.setText(context.getString(R.string.changeAt, Utils.stationFromCode(station)));
        }

        public void setDuration(String duration){
            tvChangeDuration.setText(duration);
        }
    }

    public class ViewHolderLeg extends RecyclerView.ViewHolder{
        @BindView(R.id.tvLegDepartTime) public TextView tvDepartTime;
        @BindView(R.id.tvLegDepartStatus) public TextView tvDepartStatus;
        @BindView(R.id.tvLegDepartStation) public TextView tvDepartStation;
        @BindView(R.id.tvLegDepartRealtimeStation) public TextView tvDepartRealtimeStation;
        @BindView(R.id.tvLegDepartCompany) public TextView tvDepartCompany;
        @BindView(R.id.tvLegDepartPlatform) public TextView tvDepartPlatform;
        @BindView(R.id.tvLegDuration) public TextView tvDuration;
        @BindView(R.id.tvLegArriveTime) public TextView tvArriveTime;
        @BindView(R.id.tvLegArriveStatus) public TextView tvArriveStatus;
        @BindView(R.id.tvLegArriveStation) public TextView tvArriveStation;
        @BindView(R.id.ivLegLive) public ImageView ivLive;

        public ViewHolderLeg(View v) {
            super(v);
            ButterKnife.bind(this, v);

            /* the live icon will flash when the real time info is updated */
            ivLive.setVisibility(GONE);

            /* remove the real time station text as it'll be updated as needed */
            tvDepartRealtimeStation.setVisibility(GONE);
        }

        public void setArrival(String time, String station){
            tvArriveTime.setText(time);
            tvArriveStation.setText(Utils.stationFromCode(station));
        }

        public void setDeparture(String time, String station, String platform){
            tvDepartTime.setText(time);
            tvDepartStation.setText(Utils.stationFromCode(station));

            if(platform != null && !platform.isEmpty()){
                tvDepartPlatform.setText(context.getString(R.string.platformFull, platform));
            } else {
                tvDepartPlatform.setVisibility(View.GONE);
            }
        }

        public void setCompany(String serviceProvider){
            tvDepartCompany.setText(Utils.capitalise(serviceProvider));
        }

        public void setCancelled(boolean cancelled){
            if(!cancelled) return;

            setArriveStatus("-");
            setDepartStatus("-");

            /* if the train is cancelled, make text view colours 'disabled' */
            int colour = ContextCompat.getColor(context, R.color.grayMedium);
            tvDepartTime.setTextColor(colour);
            tvDepartStation.setTextColor(colour);
            tvDepartRealtimeStation.setTextColor(colour);
            tvDepartCompany.setTextColor(colour);
            tvDepartPlatform.setTextColor(colour);
            tvDepartStatus.setTextColor(colour);
            tvArriveStatus.setTextColor(colour);
            tvArriveTime.setTextColor(colour);
            tvArriveStation.setTextColor(colour);
            tvDuration.setTextColor(colour);
        }

        public void setArriveStatus(String status){
            /* if the train is not on time, change the text color to red */
            if(status.equals(context.getString(R.string.onTime))){
                tvArriveStatus.setTextColor(ContextCompat.getColor(context, R.color.grayDark));
            } else {
                tvArriveStatus.setTextColor(ContextCompat.getColor(context, R.color.darkRed));
            }

            tvArriveStatus.setText(status);
        }

        public void setDepartStatus(String status){
            /* if the train is not on time, change the text color to red */
            if(status.equals(context.getString(R.string.onTime))){
                tvDepartStatus.setTextColor(ContextCompat.getColor(context, R.color.grayDark));
            } else {
                tvDepartStatus.setTextColor(ContextCompat.getColor(context, R.color.darkRed));
            }

            tvDepartStatus.setText(status);
        }

        public void setRealtimeStation(String station, int stringResource){
            tvDepartRealtimeStation.setVisibility(View.VISIBLE);
            tvDepartRealtimeStation.setText(context.getString(stringResource, station));
            doLiveAnimation();
        }

        public void setDuration(String departTime, String arriveTime){
            tvDuration.setText(Utils.getTimeDifference(arriveTime, departTime));
        }

        public void doLiveAnimation(){
            ivLive.setAlpha(0.f);
            ivLive.setVisibility(View.VISIBLE);
            ivLive.animate().setDuration(1000).setInterpolator(new LinearInterpolator()).alpha(1.f).start();
        }
    }

    public JourneyDetailsAdapter(Journey journey, HashMap<String, RealtimeData> realtimeData, Context context) {
        this.journey = journey;
        this.realtimeData = realtimeData;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position){
        /* the number of elements in our list is equal to the number of legs + the number of
           changes, so we check if this index is a leg or a change */
        return isChange(position) ? VIEW_TYPE_CHANGE : VIEW_TYPE_LEG;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        /* return the corresponding view holder depending on if this element is a leg or a change */
        if(viewType == VIEW_TYPE_LEG){
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_journey_list_leg, null);
            return new ViewHolderLeg(v);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_journey_list_change, null);
            return new ViewHolderChange(v);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        /* set up our different view holders depending on view type */
        if(holder.getItemViewType() == VIEW_TYPE_LEG) {
            setupViewHolderLeg(holder, position);
        } else {
            setupViewHolderChange(holder, position);
        }
    }

    private void setupViewHolderChange(RecyclerView.ViewHolder h, int position){
        ViewHolderChange holder = (ViewHolderChange) h;

        /* because we're in a change holder, we know our position is odd,
           (2 legs = leg, change, leg && 3 legs = leg, change, leg, change, leg)
           so to get the leg before the change we can go back one index */
        Leg leg = journey.getLegs().get((position - 1 ) / 2);
        Leg nextLeg = journey.getLegs().get((position + 1 ) / 2);

        String arriveTime = Utils.getBestArriveTime(leg.getDestination());
        String departTime = Utils.getBestDepartTime(nextLeg.getOrigin());

        String waitDuration = Utils.getTimeDifference(departTime, arriveTime);
        holder.setDuration(waitDuration);

        holder.setStation(leg.getDestination().getStationCode());
    }

    private void setupViewHolderLeg(RecyclerView.ViewHolder h, int position){
        ViewHolderLeg holder = (ViewHolderLeg) h;

        /* because we're in the leg view holder, this means the position will always be even,
           (2 legs = leg, change, leg && 3 legs = leg, change, leg, change, leg)
           so our division by 2 is safe */
        Leg leg = journey.getLegs().get(position / 2);

        String arriveTime = Utils.getFormattedTime(leg.getDestination().getScheduledTime());
        String arriveStation = leg.getDestination().getStationCode();
        holder.setArrival(arriveTime, arriveStation);

        String departTime = Utils.getFormattedTime(leg.getOrigin().getScheduledTime());
        String departStation = leg.getOrigin().getStationCode();
        String departPlatform = leg.getOrigin().getPlatform();
        holder.setDeparture(departTime, departStation, departPlatform);

        /* set the service provider if the transport mode is a train, else set it to "Walk" */
        holder.setCompany(leg.getTransportMode().equals("Train") ? leg.getServiceProviderName() : "Walk");

        String bestArriveTime = Utils.getBestArriveTime(leg.getDestination());
        String bestDepartTime = Utils.getBestDepartTime(leg.getOrigin());

        /* if there is real time data (means train is probably delayed) use this to calculate
           the wait duration */
        if(leg.getOrigin().getRealTime() == null){
            holder.setDepartStatus(context.getString(R.string.onTime));
        } else {
            holder.setDepartStatus("Exp " + Utils.getFormattedTime(bestDepartTime));
        }

        if(leg.getDestination().getRealTime() == null){
            holder.setArriveStatus(context.getString(R.string.onTime));
        } else {
            holder.setArriveStatus("Exp " + Utils.getFormattedTime(bestArriveTime));
        }

        holder.setDuration(bestDepartTime, bestArriveTime);

        /* we don't always have real time data */
        if(realtimeData != null && realtimeData.get(leg.getTrainId()).getIsRealTimeDataAvailable()){
            List<Stop> stops = realtimeData.get(leg.getTrainId()).getService().getStops();

            for(int i = 0; i < stops.size(); i++){
                Stop stop = stops.get(i);

                boolean startingStation = stop.getArrival().getNotApplicable() != null && stop.getArrival().getNotApplicable();
                boolean endingStation = stop.getDeparture().getNotApplicable() != null && stop.getDeparture().getNotApplicable();

                String station = Utils.stationFromCode(stop.getLocation().getCrs());
                boolean arrived = !startingStation && stop.getArrival().getRealTime() != null && stop.getArrival().getRealTime().getRealTimeServiceInfo().getHasArrived();
                boolean departed = !endingStation && stop.getDeparture().getRealTime() != null && stop.getDeparture().getRealTime().getRealTimeServiceInfo().getHasDeparted();

                if(arrived && !departed){
                    Log.d("Currently at", station);
                    holder.setRealtimeStation(station, R.string.realtimeCurrently);
                    break;
                }

                if(endingStation) continue;

                Stop nextStop = stops.get(i + 1);
                boolean nextArrived = nextStop.getArrival().getRealTime() != null && nextStop.getArrival().getRealTime().getRealTimeServiceInfo().getHasArrived();
                if(departed && !nextArrived){
                    String nextStation = Utils.stationFromCode(nextStop.getLocation().getCrs());
                    Log.d("Travelling to ", nextStation);
                    holder.setRealtimeStation(nextStation, R.string.realtimeTravelling);
                    break;
                }
            }
        }

        /* call this method last as it adjusts all the view's text colours and text content */
        holder.setCancelled(leg.getIsCancelled());
    }

    private boolean isChange(int position){
        return position % 2 == 1;
    }

    @Override
    public int getItemCount(){
        /* we incorporate changes into the item count:
           changes + legs = (legs * 2) - 1,

           if there are 3 legs, there will be 2 changes, so total = (3 * 2) - 1 */
        return (journey.getLegs().size() * 2) - 1;
    }
}

