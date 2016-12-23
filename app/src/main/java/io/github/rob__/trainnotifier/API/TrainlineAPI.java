package io.github.rob__.trainnotifier.API;

import io.github.rob__.trainnotifier.API.Models.API;
import io.github.rob__.trainnotifier.API.Models.Query;
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
        Call<API> getJourneys(@Body Query query);
    }

    public void getJourneys(Query query, final CustomListeners.TrainlineCallback callback){
        Call<API> request = api.getJourneys(query);

        request.enqueue(new Callback<API>() {
            @Override
            public void onResponse(Call<API> call, Response<API> response) {
                callback.onResponse(response);
            }

            @Override
            public void onFailure(Call<API> call, Throwable t) {
                callback.onFailure(t);
            }
        });
    }

}
