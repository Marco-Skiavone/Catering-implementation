package catering.businesslogic.recipe;

import java.util.ArrayList;

public class RecipeManager {
    ArrayList<Task> jobs;
    public RecipeManager() {
        this.jobs = Recipe.loadAllRecipes();
    }

    public ArrayList<Task> getRecipes() {
        return Task.getAllRecipes();
    }
}
