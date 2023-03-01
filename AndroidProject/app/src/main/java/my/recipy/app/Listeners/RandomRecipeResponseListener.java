package my.recipy.app.Listeners;

import my.recipy.app.Models.RandomRecipeResponse;

public interface RandomRecipeResponseListener {
    void  didFetch(RandomRecipeResponse response, String message);
    void  didError(String message);
}
