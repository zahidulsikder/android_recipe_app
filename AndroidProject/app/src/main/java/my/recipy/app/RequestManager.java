package my.recipy.app;

import android.content.Context;

import my.recipy.app.Listeners.RandomRecipeResponseListener;
import my.recipy.app.Models.RandomRecipeResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class RequestManager {

    Context context;
    static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.spoonacular.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public RequestManager(Context context) {
        this.context = context;
    }

    private interface CallRandomRecipes {
        @GET("recipes/random")
        Call<RandomRecipeResponse> callRandomRecipe(
                @Query("apiKey") String apiKey,
                @Query("number") String number
        );

    }

        public  void  getRandomRecipes(RandomRecipeResponseListener listener){
            CallRandomRecipes callRandomRecipes=retrofit.create(CallRandomRecipes.class);
            Call<RandomRecipeResponse> call = callRandomRecipes.callRandomRecipe(context.getString(R.string.app_key), "10");

            call.enqueue(new Callback<RandomRecipeResponse>() {
                @Override
                public void onResponse(Call<RandomRecipeResponse> call, Response<RandomRecipeResponse> response) {
                    if(!response.isSuccessful()){
                        listener.didError(response.message());
                        return;
                    }
                    listener.didFetch(response.body(), response.message());
                }

                @Override
                public void onFailure(Call<RandomRecipeResponse> call, Throwable t) {
                        listener.didError(t.getMessage());
                }
            });
        }



}
