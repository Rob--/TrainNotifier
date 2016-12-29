package io.github.rob__.trainnotifier.API;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import io.github.rob__.trainnotifier.API.Models.Realtime.RealtimeData;
import io.github.rob__.trainnotifier.CustomListeners;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public class RealtimeAPI {

    private static final String URL = "https://realtime.thetrainline.com";

    private final Retrofit retrofit = new Retrofit
            .Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create()).build();

    private final RealtimeInterface api = retrofit.create(RealtimeInterface.class);

    public interface RealtimeInterface {
        @Headers({"X-Api-Version: 1.0.0", "X-Platform-Type: Android", "X-Consumer-Version: 930"})
        @GET("callingpattern/{trainId}/{date}")
        Call<RealtimeData> getRealtimeData(@Path("trainId") String trainId, @Path("date") String date);
    }

    public void getRealtimeData(String trainId, final CustomListeners.RealtimeCallback callback){
        String date = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
        Call<RealtimeData> request = api.getRealtimeData(trainId, date);

        request.enqueue(new Callback<RealtimeData>() {
            @Override
            public void onResponse(Call<RealtimeData> call, Response<RealtimeData> response) {
                callback.onResponse(response);
            }

            @Override
            public void onFailure(Call<RealtimeData> call, Throwable t) {
                callback.onFailure(t);
            }
        });
    }

}
