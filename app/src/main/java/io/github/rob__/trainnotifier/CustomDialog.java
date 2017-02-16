package io.github.rob__.trainnotifier;

import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.rob__.trainnotifier.API.Models.Mobile.Journey;
import io.github.rob__.trainnotifier.API.Models.Mobile.Leg;
import io.github.rob__.trainnotifier.Utils.Utils;

import static io.github.rob__.trainnotifier.Trains.TrainJourneyAdapter.SAVED_JOURNEY_ADAPTER;

public class CustomDialog {

    public static final int SAVED_JOURNEY_DIALOG = 0;
    public static final int SEARCH_RESULT_DIALOG = 1;

    @BindView(R.id.tvArriveTime) public TextView tvArriveTime;
    @BindView(R.id.tvArrivePlatform) public TextView tvArrivePlatform;
    @BindView(R.id.tvArriveStation) public TextView tvArriveStation;
    @BindView(R.id.tvDepartTime) public TextView tvDepartTime;
    @BindView(R.id.tvDepartPlatform) public TextView tvDepartPlatform;
    @BindView(R.id.tvDepartStation)public TextView tvDepartStation;
    @BindView(R.id.tvRoute) public TextView tvRoute;
    @BindView(R.id.tvChanges) public TextView tvChanges;
    @BindView(R.id.tvDuration) public TextView tvDuration;

    /* following three are made invisible depending on the context of the usage */
    @BindView(R.id.ivDelete) public ImageView ivDelete;
    @BindView(R.id.tvPollTime) public TextView tvPollTime;
    @BindView(R.id.sbPollTime) public SeekBar sbPollTime;
    @BindView(R.id.tvPollAt) public TextView tvPollAt;

    private AlertDialog dialog;
    private final AlertDialog.Builder builder;
    private final FragmentActivity context;
    private View view;
    private int adapterContext;

    public CustomDialog(final FragmentActivity context, int title, int adapterContext){
        this.context = context;
        this.builder = new AlertDialog.Builder(context);
        this.builder.setTitle(context.getString(title));
        this.adapterContext = adapterContext;
    }

    public void display(){
        dialog = builder.create();
        dialog.show();
    }

    public void cancel(){
        dialog.cancel();
    }

    public void setOnClickListener(View.OnClickListener listener){
        Button btnConfirm = (Button) view.findViewById(R.id.btnConfirm);
        btnConfirm.setOnClickListener(listener);
    }

    public int getPollingTime(){
        return (sbPollTime.getProgress() + 1) * 5;
    }

    public void updateViews(Journey journey, boolean showPollingOptions){
        view = context.getLayoutInflater().inflate(R.layout.confirm_dialog, null);
        builder.setView(view);

        ButterKnife.bind(this, view);

        this.sbPollTime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                /* decreased max by 1, add it back here - this means that our minimum time
                   to choose from is 5 minutes */
                int minutes = (progress + 1) * 5;
                tvPollTime.setText(context.getResources().getQuantityString(R.plurals.polling, minutes, minutes));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        List<Leg> legs = journey.getLegs();

        String arriveTime = Utils.getFormattedTime(legs.get(legs.size() - 1).getDestination().getScheduledTime());
        String arriveStation = journey.getDestination();
        String arrivePlatform = legs.get(legs.size() - 1).getDestination().getPlatform();
        setArrival(arriveTime, arriveStation, arrivePlatform);

        String departTime = Utils.getFormattedTime(legs.get(0).getOrigin().getScheduledTime());
        String departStation = journey.getOrigin();
        String departPlatform = legs.get(0).getOrigin().getPlatform();
        setDeparture(departTime, departStation, departPlatform);

        String destinationCode = journey.getLegs().get(0).getDestination().getStationCode();
        setRoute(Utils.stationFromCode(destinationCode));

        setDuration(journey.getDepartureDateTime(), journey.getArrivalDateTime());

        if(adapterContext == SAVED_JOURNEY_DIALOG){
            displayPollTime(true);
            double pollingTime = (double) journey.getAdditionalProperties().get("pollTime");
            setPollAtTime(departTime, (int) pollingTime);
        } else {
            displayPollTime(false);
        }

        int changes = journey.getLegs().size() - 1;
        setChanges(changes);

        /* we reuse the same layout that displays a journey and it's info, but this layout
           includes the image view for the delete icon (used only when showing saved journeys)
           and we don't want to show it when asking the user if they want to poll the train */
        displayDeleteIcon(false);
        displayPollingOptions(showPollingOptions);
    }

    private void setArrival(String time, String station, String platform){
        tvArriveTime.setText(time);
        tvArriveStation.setText(station);

        if(platform != null && !platform.isEmpty()){
            tvArrivePlatform.setText(context.getString(R.string.platform, platform));
        } else {
            tvArrivePlatform.setText("");
        }
    }

    private void setDeparture(String time, String station, String platform){
        tvDepartTime.setText(time);
        tvDepartStation.setText(station);

        if(platform != null && !platform.isEmpty()){
            tvDepartPlatform.setText(context.getString(R.string.platform, platform));
        } else {
            tvDepartPlatform.setText("");
        }
    }

    private void setRoute(String station){
        tvRoute.setText(context.getString(R.string.route, station));
    }

    private void setDuration(String departTime, String arriveTime){
        tvDuration.setText(Utils.getDuration(departTime, arriveTime));
    }

    private void setChanges(int changes){
        if(changes == 0) {
            tvChanges.setText(context.getString(R.string.direct));
        } else {
            tvChanges.setText(context.getResources().getQuantityString(R.plurals.changes, changes, changes));
        }
    }

    private void displayDeleteIcon(boolean display) {
        if (!display) ivDelete.setVisibility(View.GONE);
    }

    private void displayPollingOptions(boolean display) {
        if(!display){
            tvPollTime.setVisibility(View.GONE);
            sbPollTime.setVisibility(View.GONE);
        }
    }

    public void displayPollTime(boolean display){
        if(!display) tvPollAt.setVisibility(View.GONE);
    }

    public void setPollAtTime(String departure, int minutes){
        try {
            Date departTime = new SimpleDateFormat("HH:mm").parse(departure);
            Calendar c = Calendar.getInstance();
            c.setTime(departTime);
            c.add(Calendar.MINUTE, -minutes);

            String time = new SimpleDateFormat("HH:mm").format(c.getTime());
            tvPollAt.setText(context.getString(R.string.poll, time));
        } catch(ParseException e){
            displayPollTime(false);
            return;
        }
    }
}
