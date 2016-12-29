package io.github.rob__.trainnotifier.API;

import io.github.rob__.trainnotifier.API.Models.Mobile.JourneyData;
import io.github.rob__.trainnotifier.API.Models.Mobile.Query;
import io.github.rob__.trainnotifier.CustomListeners;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public class TrainlineAPI {

    private static final String URL = "https://api.thetrainline.com";

    private final Retrofit retrofit = new Retrofit
            .Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create()).build();

    private final TrainlineInterface api = retrofit.create(TrainlineInterface.class);

    public interface TrainlineInterface {
        @Headers({"X-Api-Version: 2.0", "X-Platform-Type: Android", "X-Consumer-Version: 930"})
        @POST("mobile/journeys")
        Call<JourneyData> getJourneys(@Body Query query);
    }

    public void getJourneys(Query query, final CustomListeners.TrainlineCallback callback){
        Call<JourneyData> request = api.getJourneys(query);

        request.enqueue(new Callback<JourneyData>() {
            @Override
            public void onResponse(Call<JourneyData> call, Response<JourneyData> response) {
                callback.onResponse(response);
            }

            @Override
            public void onFailure(Call<JourneyData> call, Throwable t) {
                callback.onFailure(t);
            }
        });
    }

}
