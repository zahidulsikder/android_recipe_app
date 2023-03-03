package my.recipy.app;

import android.content.Context;

import java.util.List;

import my.recipy.app.Listeners.RandomRecipeResponseListener;
import my.recipy.app.Listeners.RecipeDetailsListener;
import my.recipy.app.Models.RandomRecipeResponse;
import my.recipy.app.Models.RecipeDetailsResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
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
                @Query("number") String number,
                @Query("tags")List<String> tags
                );

    }




        public  void  getRandomRecipes(RandomRecipeResponseListener listener, List<String> tags){
            CallRandomRecipes callRandomRecipes=retrofit.create(CallRandomRecipes.class);
            Call<RandomRecipeResponse> call = callRandomRecipes.callRandomRecipe(context.getString(R.string.api_key), "10", tags);

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



    private  interface  CallRecipeDetails{
        @GET("recipes/{id}/information")
        Call<RecipeDetailsResponse> callRecipeDetails(
                @Path("id") int id,
                @Query("apiKey") String apiKey
        );
    }

    public void getRecipeDetails(RecipeDetailsListener listener, int id){
        CallRecipeDetails callRecipeDetails = retrofit.create(CallRecipeDetails.class);
        Call<RecipeDetailsResponse> call = callRecipeDetails.callRecipeDetails(id, context.getString(R.string.api_key));
        call.enqueue(new Callback<RecipeDetailsResponse>() {
            @Override
            public void onResponse(Call<RecipeDetailsResponse> call, Response<RecipeDetailsResponse> response) {
                if(!response.isSuccessful()){
                    listener.didError(response.message());
                    return;
                }
                listener.didFetch(response.body(), response.message());
            }

            @Override
            public void onFailure(Call<RecipeDetailsResponse> call, Throwable t) {
                listener.didError(t.getMessage());

            }
        });
    }




}
