package my.recipy.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import my.recipy.app.Adapters.IngredientsAdapter;
import my.recipy.app.Listeners.RecipeDetailsListener;
import my.recipy.app.Models.RecipeDetailsResponse;

public class RecipeDetailActivity extends AppCompatActivity {

    int id;
    TextView textView_meal_name, textView_meal_source, textView_meal_Summary;
    ImageView imageView_meal_image;
    RecyclerView recycler_meal_ingredients;
    RequestManager manager;
    ProgressDialog dialog;
    IngredientsAdapter ingredientsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);



        findViews();

        id= Integer.parseInt(getIntent().getStringExtra("id"));
        manager= new RequestManager(this);
        manager.getRecipeDetails(recipeDetailsListener,id);
        dialog = new ProgressDialog(this);
        dialog.setTitle("Loding Details...");
        dialog.show();
    }

    private void findViews() {
        textView_meal_name= findViewById(R.id.textView_meal_name);
        textView_meal_source= findViewById(R.id.textView_meal_source);
        textView_meal_Summary= findViewById(R.id.textView_meal_Summary);
        imageView_meal_image= findViewById(R.id.imageView_meal_image);
        recycler_meal_ingredients= findViewById(R.id.recycler_meal_ingredients);

    }

    private  final RecipeDetailsListener recipeDetailsListener = new RecipeDetailsListener() {
        @Override
        public void didFetch(RecipeDetailsResponse response, String message) {
            dialog.dismiss();
            textView_meal_name.setText(response.title);
            textView_meal_source.setText(response.sourceName);
            textView_meal_Summary.setText(response.summary);
            Picasso.get().load(response.image).into(imageView_meal_image);
            recycler_meal_ingredients.setHasFixedSize(true);
            recycler_meal_ingredients.setLayoutManager(new LinearLayoutManager(RecipeDetailActivity.this,LinearLayoutManager.HORIZONTAL, false));
            ingredientsAdapter = new IngredientsAdapter(RecipeDetailActivity.this, response.extendedIngredients);
            recycler_meal_ingredients.setAdapter(ingredientsAdapter);

        }

        @Override
        public void didError(String message) {
            Toast.makeText(RecipeDetailActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    };

}