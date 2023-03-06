package my.recipy.app.Listeners;

import my.recipy.app.Models.RecipeDetailsResponse;

public interface RecipeDetailsListener {
    void didFetch(RecipeDetailsResponse response, String message);
    void  didError(String message);
}
