package io.github.rob__.trainnotifier.Search;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.rob__.trainnotifier.CustomListeners;
import io.github.rob__.trainnotifier.R;
import io.github.rob__.trainnotifier.Utils;

public class RecentSearchAdapter extends RecyclerView.Adapter<RecentSearchAdapter.ViewHolder> {

    private static CustomListeners.RecentSearchListener clickListener;
    private static String[][] recentSearches;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tvRecentFrom) public TextView tvFrom;
        @BindView(R.id.tvRecentTo) public TextView tvTo;
        @BindView(R.id.tvRecentRoute) public TextView tvRoute;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    clickListener.recentSearchClicked(recentSearches[position]);
                }
            });
        }

        public void setDeparture(String stationCode){
            tvFrom.setText(stationCode);
        }

        public void setArrival(String stationCode){
            tvTo.setText(stationCode);
        }

        public void setRoute(String arrivalCode, String departureCode){
            tvRoute.setText(Utils.getRoute(arrivalCode, departureCode));
        }
    }

    public RecentSearchAdapter(String[][] recentSearches, CustomListeners.RecentSearchListener clickListener) {
        RecentSearchAdapter.recentSearches = recentSearches;
        RecentSearchAdapter.clickListener = clickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recent_search, null);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String[] recentSearch = recentSearches[position];

        String originCode = Utils.codeFromStation(recentSearch[0]);
        String destinationCode = Utils.codeFromStation(recentSearch[1]);

        holder.setDeparture(originCode);
        holder.setArrival(destinationCode);
        holder.setRoute(originCode, destinationCode);
    }

    @Override
    public int getItemCount() {
        return recentSearches == null ? 0 : recentSearches.length;
    }
}
